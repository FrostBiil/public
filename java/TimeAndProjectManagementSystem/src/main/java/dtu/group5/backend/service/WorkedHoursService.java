package dtu.group5.backend.service;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.CoworkerRepository;
import dtu.group5.backend.repository.IRepository;
import dtu.group5.backend.repository.ProjectRepository;
import dtu.group5.backend.service.activity.edit_worked_hours.SetWorkedHoursEditor;
import dtu.group5.backend.service.activity.edit_worked_hours.WorkedHoursEditor;
import dtu.group5.frontend.session.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.formatDate;
import static dtu.group5.backend.util.DateUtil.getCurrentDate;
import static dtu.group5.backend.util.DateUtil.stripTime;

public class WorkedHoursService {
  private final IRepository<String, BaseActivity> activityRepository = ActivityRepository.getInstance();
  private final IRepository<Integer, Project> projectRepository = ProjectRepository.getInstance();
  private final IRepository<String, Coworker> coworkerRepositiory = CoworkerRepository.getInstance();


  private final List<WorkedHoursEditor> workedHoursEditors = List.of(
    new SetWorkedHoursEditor()
  );

  public List<ProjectActivity> listUnregisteredActivitiesToday(String initials) {
    Date today = stripTime(new Date());

    return coworkerRepositiory.get(initials)
      .map(coworker ->
        activityRepository.getList().stream()
          .filter(a -> a instanceof ProjectActivity)
          .map(a -> (ProjectActivity) a)
          .filter(a -> !a.isFinished())
          .filter(a -> {
            Map<Date, Double> map = a.getWorkedHoursPerCoworker().get(coworker);
            return map == null || !map.containsKey(today);
          })
          .toList()
      ).orElse(List.of());
  }

  public Optional<String> registerWorkedHours(int projectNumber, String activityTitle, double hours) {
    Coworker coworker = Session.getInstance().getLoggedInUser();
    if (coworker == null) return Optional.of("Coworker not logged in");
    if (hours < 0) return Optional.of("Worked time must be positive");

    Optional<Project> projectOpt = projectRepository.get(projectNumber);
    if (projectOpt.isEmpty()) return Optional.of("Project not found");

    Optional<ProjectActivity> activityOpt = activityRepository.getList().stream()
      .filter(a -> a instanceof ProjectActivity)
      .map(a -> (ProjectActivity) a)
      .filter(a -> a.getTitle().equals(activityTitle))
      .filter(a -> a.getProjectNumber() == projectNumber)
      .findFirst();
    if (activityOpt.isEmpty()) return Optional.of("Activity not found");

    ProjectActivity activity = activityOpt.get();

    if (activity.isFinished()) return Optional.of("Cannot register time on a completed activity");

    Date today = stripTime(new Date());

    Map<Coworker, Map<Date, Double>> allWorked = activity.getWorkedHoursPerCoworker();
    Map<Date, Double> dailyMap = allWorked.computeIfAbsent(coworker, k -> new HashMap<>());

    if (dailyMap.containsKey(today)) {
      return Optional.of("Worked hours already registered for today");
    }

    dailyMap.put(today, hours);

    if (!activity.isCoworkerAssigned(coworker)) {
      activity.addAssignedCoworker(coworker);
    }

    Project project = projectOpt.get();
    if (project.getStatus() == Project.ProjectStatus.NOT_STARTED) {
      project.setStatus(Project.ProjectStatus.IN_PROGRESS);
    }

    activityRepository.save();

    return Optional.empty();
  }

  public Optional<String> deleteWorkedHours(int projectNumber, String activityTitle, Date date) {
    Coworker coworker = Session.getInstance().getLoggedInUser();
    if (coworker == null) return Optional.of("Coworker not logged in");

    date = stripTime(date);

    Optional<Project> projectOpt = projectRepository.get(projectNumber);
    if (projectOpt.isEmpty()) return Optional.of("Project not found");

    Optional<ProjectActivity> activityOpt = activityRepository.getList().stream()
      .filter(a -> a instanceof ProjectActivity)
      .map(a -> (ProjectActivity) a)
      .filter(a -> a.getTitle().equals(activityTitle))
      .filter(a -> a.getProjectNumber() == projectNumber)
      .findFirst();
    if (activityOpt.isEmpty()) return Optional.of("Activity not found");

    ProjectActivity activity = activityOpt.get();
    Map<Coworker, Map<Date, Double>> workedHours = activity.getWorkedHoursPerCoworker();
    Map<Date, Double> dailyMap = workedHours.get(coworker);

    if (dailyMap == null || !dailyMap.containsKey(date)) {
      return Optional.of("No worked hours found for this coworker on that date");
    }

    dailyMap.remove(date);

    if (dailyMap.isEmpty()) {
      workedHours.remove(coworker);
      activity.getAssignedCoworkers().removeIf(c -> c.getInitials().equals(coworker.getInitials()));
    }

    activityRepository.save();
    return Optional.empty();
  }

  public List<String[]> getWorkedHoursByCoworker(String initials, Date date) {
    Coworker coworker = coworkerRepositiory.get(initials).orElse(null);
    if (coworker == null) return List.of();

    Date strippedDate = stripTime(date);
    List<String[]> rows = new ArrayList<>();

    for (BaseActivity a : activityRepository.getList()) {
      if (!(a instanceof ProjectActivity activity)) continue;
      Map<Date, Double> worked = activity.getWorkedHoursPerCoworker().getOrDefault(coworker, Map.of());
      if (!worked.containsKey(strippedDate)) continue;

      rows.add(new String[]{
        String.valueOf(activity.getProjectNumber()),
        activity.getTitle(),
        String.valueOf(worked.get(strippedDate)),
        formatDate(strippedDate)
      });
    }

    return rows;
  }

  public Optional<String> editWorkedHours(int projectNumber, String activityTitle, String initials, String fieldName, double hours, Date date) {
    if (date == null) date = getCurrentDate();
    if (hours < 0) return Optional.of("Worked hours must be positive");

    Coworker coworker = coworkerRepositiory.get(initials).orElse(null);
    if (coworker == null) return Optional.of("Coworker not found");

    Optional<ProjectActivity> activityOpt = activityRepository.getList().stream()
      .filter(a -> a instanceof ProjectActivity)
      .map(a -> (ProjectActivity) a)
      .filter(a -> a.getTitle().equals(activityTitle))
      .filter(a -> a.getProjectNumber() == projectNumber)
      .findFirst();
    if (activityOpt.isEmpty()) return Optional.of("Activity not found");

    ProjectActivity activity = activityOpt.get();

    Date finalDate = date;
    return workedHoursEditors.stream()
      .filter(editor -> editor.supports(fieldName))
      .findFirst()
      .map(editor -> {
        Optional<String> error = editor.edit(activity, coworker, finalDate, hours);
        if (error.isEmpty()) activityRepository.save();
        return error;
      })
      .orElse(Optional.of("Unsupported field: " + fieldName));
  }
}

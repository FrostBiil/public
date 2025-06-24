package dtu.group5.backend.service;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.CoworkerRepository;
import dtu.group5.backend.repository.IRepository;
import dtu.group5.backend.repository.ProjectRepository;
import dtu.group5.backend.service.coworker.edit_coworker.CoworkerFieldEditor;
import dtu.group5.backend.service.coworker.edit_coworker.NameEditor;
import dtu.group5.frontend.session.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dtu.group5.backend.util.DateUtil.formatDate;
import static dtu.group5.backend.util.DateUtil.stripTime;
import static dtu.group5.backend.util.FieldParser.parseField;

public class CoworkerService {
  private final IRepository<String, Coworker> coworkerRepository = CoworkerRepository.getInstance();
  private final IRepository<String, BaseActivity> activityRepository = ActivityRepository.getInstance();
  private final IRepository<Integer, Project> projectRepository = ProjectRepository.getInstance();

  private final List<CoworkerFieldEditor> editors = List.of(
          new NameEditor()
  );

  public Optional<String> create(String initials, String name) {
    if (initials == null || initials.isBlank()) return Optional.of("Initials cannot be empty");
    if (initials.length() > 4) return Optional.of("Initials must be at most 4 characters");
    if (name == null || name.isBlank()) return Optional.of("Name cannot be empty");
    if (coworkerRepository.contains(initials)) return Optional.of("Initials already in use");

    coworkerRepository.add(new Coworker(initials, name));
    return Optional.empty(); // success
  }

  public Optional<String> create(Coworker coworker) {
    if (coworker == null) return Optional.empty();
    // Validate coworker properties
    return create(coworker.getInitials(), coworker.getName());
  }

  public Optional<Coworker> get(String initials) {
    return coworkerRepository.get(initials);
  }

  public List<Coworker> getList() {
    return coworkerRepository.getList();
  }

  public Optional<String> editCoworker(Coworker coworker, Map<String, Object> fieldsToChange) {

    if (coworker == null) return Optional.of("Coworker not found");

    Coworker requester = Session.getInstance().getLoggedInUser();

    if (requester == null) return Optional.of("You must be logged in to edit a coworker");

    if (!requester.getInitials().equalsIgnoreCase(coworker.getInitials())) return Optional.of("A coworker can only edit their own information");


    for (Map.Entry<String, Object> entry : fieldsToChange.entrySet()) {
      String field = entry.getKey();

      try {
        Object value = parseField(field, entry.getValue());

        Optional<CoworkerFieldEditor> editorOpt = editors.stream()
                .filter(u -> u.supports(field))
                .findFirst();

        if (editorOpt.isEmpty()) return Optional.of("Unknown field: " + field);

        Optional<String> error = editorOpt.get().edit(coworker, value);
        if (error.isPresent())  return error;
      } catch (Exception e) {
        return Optional.of(e.getMessage());
      }
    }

    this.coworkerRepository.save();
    return Optional.empty();
  }

  public Optional<String> removeCoworker(Coworker coworker) {
    if (coworker == null) return Optional.of("Coworker cannot be null");
    if (!coworkerRepository.contains(coworker.getInitials())) return Optional.of("Coworker not found");
    if (coworker.getInitials().equalsIgnoreCase(Session.getInstance().getLoggedInUser().getInitials()))
      return Optional.of("You cannot delete the user that is currently logged in");

    if (!getCoworkerActivities(coworker).isEmpty()) return Optional.of("Coworker is assigned to activities, please remove them first");

    // Remove as project leader if assigned in any project
    for (Project project : projectRepository.getList()) {
      if (project.getProjectLeader() != null && project.getProjectLeader().getInitials().equalsIgnoreCase(coworker.getInitials())) {
        return Optional.of("Coworker is a project leader in project ("+project.getProjectNumber()+"). Please remove them first");
      }
    }


    coworkerRepository.remove(coworker.getInitials());
    return Optional.empty();
  }

  public Set<BaseActivity> getCoworkerActivities(Coworker coworker) {
    if (coworker == null) return Set.of();

    return this.activityRepository.getList().stream().filter(a ->a.isCoworkerAssigned(coworker)).collect(Collectors.toSet());
  }

  public List<String[]> getRegisteredTimeEntries(String initials) {
    Optional<Coworker> coworkerOpt = coworkerRepository.get(initials);
    if (coworkerOpt.isEmpty()) return Collections.emptyList();
    Coworker coworker = coworkerOpt.get();

    List<String[]> result = new ArrayList<>();
    Set<BaseActivity> allActivities = getCoworkerActivities(coworker);

    for (BaseActivity base : allActivities) {
      if (!(base instanceof ProjectActivity activity)) continue;

      String projectTitle = String.valueOf(activity.getProjectNumber());
      String activityTitle = activity.getTitle();

      Map<Date, Double> worked = activity.getWorkedHoursPerCoworker().getOrDefault(coworker, Collections.emptyMap());

      for (Map.Entry<Date, Double> entry : worked.entrySet()) {
        String formattedDate = formatDate(entry.getKey());

        result.add(new String[]{
          projectTitle,
          activityTitle,
          formattedDate,
          String.valueOf(entry.getValue())
        });
      }
    }

    return result;
  }

  public List<String[]> getRegisteredTimeEntriesToday(String initials) {
    Optional<Coworker> coworkerOpt = coworkerRepository.get(initials);
    if (coworkerOpt.isEmpty()) return Collections.emptyList();
    Coworker coworker = coworkerOpt.get();

    List<String[]> result = new ArrayList<>();
    Set<BaseActivity> allActivities = getCoworkerActivities(coworker);
    Date today = stripTime(new Date());

    for (BaseActivity base : allActivities) {
      if (!(base instanceof ProjectActivity activity)) continue;

      int projectNumber = activity.getProjectNumber();
      String activityTitle = activity.getTitle();

      Map<Date, Double> worked = activity.getWorkedHoursPerCoworker()
        .getOrDefault(coworker, Collections.emptyMap());

      for (Map.Entry<Date, Double> entry : worked.entrySet()) {
        if (stripTime(entry.getKey()).equals(today)) {
          result.add(new String[]{
            String.valueOf(projectNumber),
            activityTitle,
            formatDate(entry.getKey()),
            String.valueOf(entry.getValue())
          });
        }
      }
    }

    return result;
  }
}

package dtu.group5.backend.service;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.CoworkerRepository;
import dtu.group5.backend.repository.IRepository;
import dtu.group5.backend.repository.ProjectRepository;
import dtu.group5.backend.service.project.create_project.DescriptionCreator;
import dtu.group5.backend.service.project.create_project.EndDateCreator;
import dtu.group5.backend.service.project.create_project.ProjectFieldCreator;
import dtu.group5.backend.service.project.create_project.ProjectLeaderCreator;
import dtu.group5.backend.service.project.create_project.ProjectTypeCreator;
import dtu.group5.backend.service.project.create_project.StartDateCreator;
import dtu.group5.backend.service.project.create_project.TitleCreator;
import dtu.group5.backend.service.project.edit_project.DescriptionEditor;
import dtu.group5.backend.service.project.edit_project.EndDateEditor;
import dtu.group5.backend.service.project.edit_project.ProjectFieldEditor;
import dtu.group5.backend.service.project.edit_project.ProjectLeaderEditor;
import dtu.group5.backend.service.project.edit_project.StartDateEditor;
import dtu.group5.backend.service.project.edit_project.StatusEditor;
import dtu.group5.backend.service.project.edit_project.TitleEditor;
import dtu.group5.backend.service.project.edit_project.TypeEditor;
import dtu.group5.backend.service.project.validator_project.ProjectFieldValidator;
import dtu.group5.backend.service.project.validator_project.StartBeforeEndValidator;
import dtu.group5.frontend.session.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dtu.group5.backend.service.AssignmentService.isCoworkerAssigned;
import static dtu.group5.backend.util.FieldParser.parseField;

// Made by Matthias (s245759)
public class ProjectService {
  private final IRepository<String, BaseActivity> activityRepository = ActivityRepository.getInstance();
  private final IRepository<Integer, Project> projectRepository = ProjectRepository.getInstance();
  private final IRepository<String, Coworker> coworkerRepository = CoworkerRepository.getInstance();

  private final List<ProjectFieldEditor> editors = List.of(
          new TitleEditor(),
          new TypeEditor(),
          new DescriptionEditor(),
          new StartDateEditor(),
          new EndDateEditor(),
          new ProjectLeaderEditor(),
          new StatusEditor()
  );

  private final List<ProjectFieldCreator> creators = List.of(
    new TitleCreator(),
    new DescriptionCreator(),
    new StartDateCreator(),
    new EndDateCreator(),
    new ProjectLeaderCreator(),
    new ProjectTypeCreator()
  );

  private final List<ProjectFieldValidator> validators = List.of(
    new StartBeforeEndValidator()
  );

  // Made by Matthias (s245759)
  public Optional<String> create(Map<String, Object> inputFields) {
    int number = getNextProjectNumber();
    Project project = new Project(number, null);

    for (var entry : inputFields.entrySet()) {
      String field = entry.getKey();
      Object value = entry.getValue();

      Optional<ProjectFieldCreator> creatorOpt = creators.stream()
        .filter(c -> c.supports(field))
        .findFirst();

      if (creatorOpt.isEmpty()) return Optional.of("Unknown field: " + field);
      Optional<String> error = creatorOpt.get().create(project, value);
      if (error.isPresent()) return error;
    }

    for (ProjectFieldValidator validator : validators) {
      Optional<String> error = validator.validate(project);
      if (error.isPresent()) return error;
    }

    if (projectRepository.contains(project.getProjectNumber())) {
      return Optional.of("Project number already exists");
    }
    if (project.getStartDate() != null && project.getEndDate() != null &&
            project.getStartDate().after(project.getEndDate())) {
      return Optional.of("End date cannot be before start date");
    }

    projectRepository.add(project);
    return Optional.empty();
  }

  // Made by August (s241541)
  public Optional<Project> get(int projectNumber) {
    return projectRepository.get(projectNumber);
  }

  // Made by August (s241541)
  public List<Project> getList() {
    return projectRepository.getList();
  }

  // Made by Elias (241121)
  public Optional<String> editProject(Project project, Map<String, Object> fieldsToChange) {
    Coworker requester = Session.getInstance().getLoggedInUser();

    if (requester == null) return Optional.of("Please login");
    if (project.getProjectLeader() != null) {
      if (!requester.getInitials().equals(project.getProjectLeader().getInitials()))
        return Optional.of("Only the project leader can edit a project");
    } else {
      if (!isCoworkerAssigned(project, requester))
        return Optional.of("You are not assigned to this project");
    }

    for (Map.Entry<String, Object> entry : fieldsToChange.entrySet()) {
      String field = entry.getKey();

      try {
        Object value = parseField(field, entry.getValue());

        Optional<ProjectFieldEditor> editorOpt = editors.stream()
          .filter(u -> u.supports(field))
          .findFirst();

        if (editorOpt.isEmpty()) return Optional.of("Unknown field: " + field);

        Optional<String> error = editorOpt.get().edit(project, value);
        if (error.isPresent()) return error;
      } catch (Exception e) {
        return Optional.of(e.getMessage());
      }
    }
    this.projectRepository.save();
    return Optional.empty();
  }

  // Made by Jacob(s246077)
  public List<ProjectFieldCreator> getCreators() {
    return creators;
  }

  // Made by Elias (s241121)
  public Optional<String> removeCoworkerFromProject(Project project, Coworker coworker) {
      if (project == null) return Optional.of("Project cannot be null");
      if (coworker == null) return Optional.of("Coworker cannot be null");
      if (!isCoworkerAssigned(project, coworker)) return Optional.of("Coworker is not assigned to the project");
      project.removeCoworker(coworker);
      getProjectActivities(project).stream().filter(
              activity -> activity.isCoworkerAssigned(coworker))
              .forEach(activity -> activity.getAssignedCoworkers().remove(coworker)
      );
      this.projectRepository.save();
      this.activityRepository.save();
      return Optional.empty();
  }

  // Made by Elias (s241121)
  public List<String[]> generateWorkloadReport(Project project) {
    Coworker requester = Session.getInstance().getLoggedInUser();
    if (requester == null) throw new IllegalStateException("Please login");

    if (project.getProjectLeader() != null) {
      if (!requester.getInitials().equals(project.getProjectLeader().getInitials())) {
        throw new IllegalStateException("You are not the project leader");
      }
    } else if (!isCoworkerAssigned(project, requester)) {
      throw new IllegalStateException("You are not assigned to this project");
    }

    Set<ProjectActivity> activities = getProjectActivities(project);
    if (activities == null || activities.isEmpty()) return List.of();

    List<String[]> report = new ArrayList<>();
    report.add(new String[]{
      "Title", "Expected Hours", "Worked Hours", "Completed (%)", "Expected Weeks", "Finished", "Assigned Coworkers"
    });

    for (ProjectActivity activity : activities) {
      String title = activity.getTitle();
      double expectedHours = activity.getExpectedHours();

      double totalWorked = activity.getWorkedHoursPerCoworker().values().stream()
        .flatMap(map -> map.values().stream())
        .mapToDouble(Double::doubleValue)
        .sum();

      double completedPercent = expectedHours > 0 ? (totalWorked / expectedHours) * 100 : 0;
      String formattedCompleted = String.format(Locale.ENGLISH, "%.1f%%", completedPercent);

      int yearDiff = activity.getEndYear() - activity.getStartYear();
      int expectedWeeks = 1 + activity.getEndWeekNumber() - activity.getStartWeekNumber() + 52 * yearDiff;
      String finished = activity.isFinished() ? "Yes" : "No";
      int assigned = activity.getAssignedCoworkers().size();

      report.add(new String[]{
        title,
        String.valueOf(expectedHours),
        String.valueOf(totalWorked),
        formattedCompleted,
        String.valueOf(expectedWeeks),
        finished,
        String.valueOf(assigned)
      });
    }

    return report;
  }

  // Made by Elias (s241121)
  public Set<ProjectActivity> getProjectActivities(Project project) {
    if (project == null) return Set.of();
    return this.activityRepository.getList().stream()
            .filter(a -> a instanceof ProjectActivity)
            .map(a -> (ProjectActivity) a)
            .filter(a -> a.getProjectNumber() == project.getProjectNumber()).collect(Collectors.toSet());
  }

  // Made by Julius(s245723)
  public Optional<String> removeProject(Project project) {
    if (project == null) return Optional.of("Project cannot be null");

    if (Session.getInstance().getLoggedInUser() == null) return Optional.of("Please login");

    if (project.getProjectLeader() != null) {
      Coworker requester = Session.getInstance().getLoggedInUser();
      if (!requester.getInitials().equals(project.getProjectLeader().getInitials()))
        return Optional.of("Only the project leader can delete a project");
    }

    if (!getProjectActivities(project).isEmpty()) return Optional.of("Project has activities assigned to it, please remove them first");

    projectRepository.remove(project.getProjectNumber());
    return Optional.empty();
  }

  // Made by Julius (245723)
  private int getNextProjectNumber() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100;

    int lowestProjectNumber = currentYear*1000;

    int highestProjectNumber = this.projectRepository.getList().stream().mapToInt(Project::getProjectNumber).max().orElse(lowestProjectNumber);

    return highestProjectNumber + 1;
  }
}

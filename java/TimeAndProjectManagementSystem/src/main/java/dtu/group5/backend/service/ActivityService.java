package dtu.group5.backend.service;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.CoworkerRepository;
import dtu.group5.backend.repository.IRepository;
import dtu.group5.backend.repository.ProjectRepository;
import dtu.group5.backend.service.activity.edit_activity.ActivityFieldEditor;
import dtu.group5.backend.service.activity.edit_activity.DescriptionEditor;
import dtu.group5.backend.service.activity.edit_activity.FinishedEditor;
import dtu.group5.backend.service.activity.edit_activity.edit_activity_fixed.EndDateEditor;
import dtu.group5.backend.service.activity.edit_activity.edit_activity_fixed.StartDateEditor;
import dtu.group5.backend.service.activity.edit_activity.edit_activity_project.EndWeekEditor;
import dtu.group5.backend.service.activity.edit_activity.edit_activity_project.EndYearEditor;
import dtu.group5.backend.service.activity.edit_activity.edit_activity_project.ExpectedHoursEditor;
import dtu.group5.backend.service.activity.edit_activity.edit_activity_project.StartWeekEditor;
import dtu.group5.backend.service.activity.edit_activity.edit_activity_project.StartYearEditor;
import dtu.group5.backend.util.DateUtil;
import dtu.group5.frontend.session.Session;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static dtu.group5.backend.util.FieldParser.parseField;

// Made by Matthias (s245759)
public class ActivityService {
  private final IRepository<String, BaseActivity> activityRepository = ActivityRepository.getInstance();
  private final IRepository<Integer, Project> projectRepository = ProjectRepository.getInstance();
  private final IRepository<String, Coworker> coworkerRepositiory = CoworkerRepository.getInstance();
  private final AssignmentService assignmentService;
  private final CoworkerService coworkerService;

  private final List<ActivityFieldEditor> editors = List.of(
      new DescriptionEditor(),
      new FinishedEditor(),
      new EndDateEditor(),
      new StartDateEditor(),
      new EndWeekEditor(),
      new EndYearEditor(),
      new StartWeekEditor(),
      new StartYearEditor(),
      new ExpectedHoursEditor()
  );

  // Made by Matthias (s245759)
  public ActivityService(AssignmentService assignmentService, CoworkerService coworkerService) {
    this.assignmentService = assignmentService;
    this.coworkerService = coworkerService;
  }

  // Made by Elias (241121)
  public Optional<String> createProductActivity(int projectNumber, String title, double expectedHours, boolean finished, String description, Set<Coworker> assignedCoworkers, int startYear, int startWeekNumber, int endYear, int endWeekNumber, Map<Coworker, Map<Date, Double>> workedHoursPerCoworker) {
      if (title == null || title.isBlank()) return Optional.of("Activity title cannot be empty");
      if (activityRepository.contains(title)) return Optional.of("Activity already exists.");
      if (description == null || description.isBlank()) return Optional.of("Description cannot be empty");
      if (startYear < 0 || startYear >= 99) return Optional.of("Start year must be between 0 and 99");
      if (startWeekNumber < 0 || startWeekNumber >= 52) return Optional.of("Start week number must be between 0 and 52");
      if (endYear < 0 || endYear >= 99) return Optional.of("End year must be between 0 and 99");
      if (endWeekNumber < 0 || endWeekNumber >= 52) return Optional.of("End week number must be between 0 and 52");
      if (expectedHours < 0) return Optional.of("Expected hours must be non-negative.");
      if (startYear > endYear || (startYear == endYear && startWeekNumber > endWeekNumber)) return Optional.of("End date cannot be before start date");

      if (projectNumber > 0) {
        Optional<Project> projectOpt = projectRepository.get(projectNumber);
        if (projectOpt.isEmpty()) {
          return Optional.of("Project with number " + projectNumber + " not found");
        }
      }

      ProjectActivity activity = new ProjectActivity(
        projectNumber,
        title,
        expectedHours,
        finished,
        description,
        assignedCoworkers,
        startYear,
        startWeekNumber,
        endYear,
        endWeekNumber,
        workedHoursPerCoworker
      );
      activityRepository.add(activity);

      return Optional.empty();
  }

  // Made by Elias (241121)
  public Optional<String> createFixedActivity(String title, boolean finished, String description, Date startDate, Date endDate, Coworker assignedCoworker) {

    if (title == null || title.isBlank()) return Optional.of("Activity title cannot be empty");
    if (activityRepository.contains(title)) return Optional.of("Activity already exists.");
    if (description == null || description.isBlank()) return Optional.of("Description cannot be empty");
    if (startDate == null || endDate == null) return Optional.of("Start and end dates cannot be null");
    if (startDate.after(endDate)) return Optional.of("Start date cannot be after end date");
    if (assignedCoworker == null) return Optional.of("Assigned coworker cannot be null");
    if (assignedCoworker.getInitials() == null || assignedCoworker.getInitials().isBlank()) return Optional.of("Assigned coworker initials cannot be empty");
    if (assignmentService.doesCoworkerHaveActivityInTimespand(assignedCoworker, startDate, endDate)) return Optional.of("Assigned coworker is assigned to another activity during this period");

    FixedActivity activity = new FixedActivity(title, finished, description, startDate, endDate, assignedCoworker);
    activityRepository.add(activity);

    return Optional.empty();
  }

  // Made by Elias (241121)
  public Optional<String> createFixedActivity(FixedActivity fixedActvity) {
    return createFixedActivity(fixedActvity.getTitle(), fixedActvity.isFinished(), fixedActvity.getDescription(), fixedActvity.getStartDate(), fixedActvity.getEndDate(), fixedActvity.getAssignedCoworkers().stream().findFirst().orElse(null));
  }

  // Made by Elias (241121)
  public Optional<String> create(ProjectActivity projectActivity) {
     return createProductActivity(projectActivity.getProjectNumber(), projectActivity.getTitle(), projectActivity.getExpectedHours(), projectActivity.isFinished(), projectActivity.getDescription(), projectActivity.getAssignedCoworkers(), projectActivity.getStartYear(), projectActivity.getStartWeekNumber(), projectActivity.getEndYear(), projectActivity.getEndWeekNumber(), projectActivity.getWorkedHoursPerCoworker());
  }

  // Made by Elias (241121)
  public Optional<String> create(BaseActivity activity) {
    return switch (activity) {
      case null -> Optional.empty();
      case ProjectActivity projectActivity -> create(projectActivity);
      case FixedActivity fixedActivity -> createFixedActivity(fixedActivity);
      default -> Optional.of("Unknown activity type: " + activity.getClass().getSimpleName());
    };

  }

  // Made by Julius (245723)
  public Optional<BaseActivity> get(String title) {
    return activityRepository.get(title);
  }

  // Made by Julius (245723)
  public List<BaseActivity> getList() {
    return activityRepository.getList();
  }

  // Made by Julius (245723)
  public boolean contains(String title) {
    return activityRepository.contains(title);
  }

  // Made by Matthias (s245759)
  public Optional<String> editActivity(BaseActivity activity, Map<String, Object> fieldsToChange) {
      if (activity == null) return Optional.of("Activity cannot be null");
      if (fieldsToChange == null || fieldsToChange.isEmpty()) return Optional.of("No fields to change");

      // Handle Project Activity
      if (activity instanceof ProjectActivity projectActivity) {

        // Make sure only the project leader can edit the activity if one is assigned
        Coworker requester = Session.getInstance().getLoggedInUser();
        if (requester == null) return Optional.of("Please login");

        Optional<Project> projectOpt = projectRepository.get(projectActivity.getProjectNumber());
        if (projectOpt.isEmpty()) return Optional.of("Project with number " + projectActivity.getProjectNumber() + " not found");
        Project project = projectOpt.get();

        if (project.getProjectLeader() != null) {
          if (!requester.getInitials().equals(project.getProjectLeader().getInitials()))
            return Optional.of("Only the project leader can edit an activity");
        } else {
          if (project.getCoworkers().stream().noneMatch(c ->  c.getInitials().equalsIgnoreCase(requester.getInitials()))) {
            return Optional.of("You are not assigned to this activity and cannot edit it");
          }
        }
      }

      // Edit activity
      for (Map.Entry<String, Object> entry : fieldsToChange.entrySet()) {
        String field = entry.getKey();

        try {
          Object value = parseField(field, entry.getValue());

          Optional<ActivityFieldEditor> editorOpt = editors.stream()
                  .filter(u -> u.supports(field))
                  .findFirst();

          if (editorOpt.isEmpty()) return Optional.of("Unknown field: " + field);

          Optional<String> error = editorOpt.get().edit(activity, value);
          if (error.isPresent()) return error;
        } catch (Exception e) {
          return Optional.of(e.getMessage());
        }
      }
      this.activityRepository.save();
      return Optional.empty();
  }

  // Made by Auguest (s241541)
  public Optional<BaseActivity> findActivityByTitle(String activityTitle) {
    for (BaseActivity activity : activityRepository.getList()) {
      if (activity.getTitle().equals(activityTitle)) {
        return Optional.of(activity);
      }
    }
    return Optional.empty();
  }

  // Made by Jacob (s246077)
  public Optional<String> markAsFinished(BaseActivity activity) {
    if (activity == null) return Optional.of("Activity cannot be null");
    activity.setFinished(true);
    activityRepository.save();
    return Optional.empty();
  }

  // Made by Jacob (s246077)
  public Optional<String> removeActivity(BaseActivity activity) {
        if (activity == null) return Optional.of("Activity cannot be null");
        activityRepository.remove(activity.getTitle());
        return Optional.empty();
    }

  // Made by August (s241541)
  public boolean isCoworkerAvailable(Coworker coworker, BaseActivity activity){
        if (activity == null) return false;
        if (activity.getAssignedCoworkers().contains(coworker)) return false;

        switch (activity) {
            case ProjectActivity projectActivity -> {
                if (assignmentService.doesCoworkerHaveActivityInTimespand(coworker, DateUtil.convertToDate(projectActivity.getStartYear(), projectActivity.getStartWeekNumber()), DateUtil.convertToDate(projectActivity.getEndYear(), projectActivity.getEndWeekNumber()))) {
                    return false;
                }
            }
            case FixedActivity fixedActvity -> {
                if (assignmentService.doesCoworkerHaveActivityInTimespand(coworker, fixedActvity.getStartDate(), fixedActvity.getEndDate())) {
                    return false;
                }
            }
            default -> {
                return false;
            }
        }

        return true;
    }

  // Made by August (s241541)
  public List<Coworker> listAvailableCoworkers(ProjectActivity activity){
        return coworkerService.getList().stream()
                .filter(coworker -> coworkerService.getCoworkerActivities(coworker).size() < coworker.getMaxActivities())
                .filter(coworker -> isCoworkerAvailable(coworker, activity))
                .collect(Collectors.toList());
    }
}

package dtu.group5.backend.service;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.IRepository;
import dtu.group5.backend.repository.ProjectRepository;

import java.util.Date;
import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.convertToDate;

// Made by Mattias (s245759)
public class AssignmentService {
  private final IRepository<String, BaseActivity> activityRepository = ActivityRepository.getInstance();
  private final IRepository<Integer, Project> projectRepository = ProjectRepository.getInstance();
  private final CoworkerService coworkerService;

  // Made by Mattias (s245759)
  public AssignmentService(CoworkerService coworkerService) {
    this.coworkerService = coworkerService;
  }

  // Made by Mattias (s245759)
  public Optional<String> assignCoworkerToActivity(BaseActivity activity, Coworker coworker, boolean force) {
    if (activity == null || coworker == null)
      return Optional.of("Activity or coworker cannot be null");

    if (activity.getAssignedCoworkers().contains(coworker))
      return Optional.of("Coworker is already assigned to the activity");

    // Overlap-tjek
    boolean hasConflict = switch (activity) {
      case FixedActivity fa -> doesCoworkerHaveActivityInTimespand(coworker, fa.getStartDate(), fa.getEndDate());
      case ProjectActivity pa -> {
        Date start = convertToDate(pa.getStartYear(), pa.getStartWeekNumber());
        Date end = convertToDate(pa.getEndYear(), pa.getEndWeekNumber());
        yield doesCoworkerHaveActivityInTimespand(coworker, start, end);
      }
      default -> false;
    };

    if (hasConflict)
      return Optional.of("Coworker is assigned to another activity during this period");

    // Max aktivitetstjek kun for ProjectActivity
    if (activity instanceof ProjectActivity) {
      int currentCount = coworkerService.getCoworkerActivities(coworker).size();
      if (currentCount >= coworker.getMaxActivities()) {
        if (!force) return Optional.of("Coworker has reached maximum number of activities");
      }
    }

    activity.addAssignedCoworker(coworker);
    activityRepository.save();

    if (force)
      return Optional.of("Coworker has reached maximum number of activities, but activity was added anyway.");

    return Optional.empty();
  }

  // Made by Elias (241121)
  public Optional<String> assignCoworkerToProject(Project project, Coworker coworker) {
    if (project == null) return Optional.of("Project cannot be null");
    if (coworker == null) return Optional.of("Coworker cannot be null");
    if (isCoworkerAssigned(project, coworker)) return Optional.of("Coworker is already part of the project");
    project.addCoworker(coworker);
    this.projectRepository.save();
    return Optional.empty();
  }

    // Made by Mattias (s245759)
  public static boolean isCoworkerAssigned(Project project, Coworker coworker) {
    return project.getCoworkers().stream().anyMatch(c -> c.getInitials().equals(coworker.getInitials()));
  }

  // Made by Elias (241121)
  public boolean doesCoworkerHaveActivityInTimespand(Coworker coworker, Date startDate, Date endDate) {
    // Check overlap for FixedActivity
    boolean found = activityRepository.getList().stream()
      .filter(a -> a.isCoworkerAssigned(coworker))
      .filter(a -> a instanceof FixedActivity)
      .map(a -> (FixedActivity) a)
      .anyMatch(a -> !(a.getEndDate().before(startDate) || a.getStartDate().after(endDate)));

    if (found) return true;

    // Check overlap for ProjectActivity
    found = activityRepository.getList().stream()
      .filter(a -> a.isCoworkerAssigned(coworker))
      .filter(a -> a instanceof ProjectActivity)
      .map(a -> (ProjectActivity) a)
      .anyMatch(a -> {
        Date activityStart = convertToDate(a.getStartYear(), a.getStartWeekNumber());
        Date activityEnd = convertToDate(a.getEndYear(), a.getEndWeekNumber());
        return !(activityEnd.before(startDate) || activityStart.after(endDate));
      });

    return found;
  }
}

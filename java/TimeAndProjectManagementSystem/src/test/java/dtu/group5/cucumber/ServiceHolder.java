package dtu.group5.cucumber;

import dtu.group5.backend.service.ActivityService;
import dtu.group5.backend.service.AssignmentService;
import dtu.group5.backend.service.CoworkerService;
import dtu.group5.backend.service.ProjectService;
import dtu.group5.backend.service.WorkedHoursService;

public class ServiceHolder {
  private static ServiceHolder instance;


  private final ActivityService activityService;
  private final CoworkerService coworkerService;
  private final ProjectService projectService;
  private final AssignmentService assignmentService;
  private final WorkedHoursService workedHoursService;

  public static ServiceHolder getInstance() {
    if (instance == null) {
      instance = new ServiceHolder();
    }
    return instance;
  }

  private ServiceHolder() {
    coworkerService = new CoworkerService();
    projectService = new ProjectService();
    assignmentService = new AssignmentService(coworkerService);
    activityService = new ActivityService(assignmentService, coworkerService);
    workedHoursService = new WorkedHoursService();
  }

  public ActivityService getActivityService() {
    return activityService;
  }

  public CoworkerService getCoworkerService() {
    return coworkerService;
  }

  public ProjectService getProjectService() {
    return projectService;
  }

  public AssignmentService getAssignmentService() {
    return assignmentService;
  }

  public WorkedHoursService getWorkedHoursService() {
    return workedHoursService;
  }
}

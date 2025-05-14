package dtu.group5;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.CoworkerRepository;
import dtu.group5.backend.repository.ProjectRepository;
import dtu.group5.backend.service.ActivityService;
import dtu.group5.backend.service.AssignmentService;
import dtu.group5.backend.service.CoworkerService;
import dtu.group5.backend.service.ProjectService;
import dtu.group5.backend.service.WorkedHoursService;
import dtu.group5.database.DataManager;
import dtu.group5.frontend.controller.ActivityController;
import dtu.group5.frontend.controller.AssignmentController;
import dtu.group5.frontend.controller.CoworkerController;
import dtu.group5.frontend.controller.ProjectController;
import dtu.group5.frontend.controller.WorkedHoursController;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.view.ICreateView;
import dtu.group5.frontend.view.IDeleteView;
import dtu.group5.frontend.view.IEditView;
import dtu.group5.frontend.view.IListView;
import dtu.group5.frontend.view.IReportView;
import dtu.group5.frontend.view.IView;
import dtu.group5.frontend.view.LoginMenuView;
import dtu.group5.frontend.view.MainMenuView;
import dtu.group5.frontend.view.activity.ActivityAssignCoworkerView;
import dtu.group5.frontend.view.activity.ActivityCreateView;
import dtu.group5.frontend.view.activity.ActivityDeleteView;
import dtu.group5.frontend.view.activity.ActivityEditView;
import dtu.group5.frontend.view.activity.ActivityListFixedView;
import dtu.group5.frontend.view.activity.ActivityListMembersView;
import dtu.group5.frontend.view.activity.ActivityListMenuView;
import dtu.group5.frontend.view.activity.ActivityListProjectView;
import dtu.group5.frontend.view.activity.ActivityMenuView;
import dtu.group5.frontend.view.coworker.CoworkerCreateView;
import dtu.group5.frontend.view.coworker.CoworkerDeleteView;
import dtu.group5.frontend.view.coworker.CoworkerEditView;
import dtu.group5.frontend.view.coworker.CoworkerListView;
import dtu.group5.frontend.view.coworker.CoworkerMenuView;
import dtu.group5.frontend.view.project.ProjectActivityAvailableCoworkersView;
import dtu.group5.frontend.view.project.ProjectAddMemberView;
import dtu.group5.frontend.view.project.ProjectCreateView;
import dtu.group5.frontend.view.project.ProjectDeleteView;
import dtu.group5.frontend.view.project.ProjectEditView;
import dtu.group5.frontend.view.project.ProjectListActivitiesView;
import dtu.group5.frontend.view.project.ProjectListMembersView;
import dtu.group5.frontend.view.project.ProjectListView;
import dtu.group5.frontend.view.project.ProjectMenuView;
import dtu.group5.frontend.view.project.ProjectRemoveMemberView;
import dtu.group5.frontend.view.project.ProjectReportView;
import dtu.group5.frontend.view.registerHours.DeleteWorkedHoursView;
import dtu.group5.frontend.view.registerHours.EditWorkedHoursView;
import dtu.group5.frontend.view.registerHours.ListTodaysRegisteredHoursView;
import dtu.group5.frontend.view.registerHours.ListUnregisteredActivitiesView;
import dtu.group5.frontend.view.registerHours.RegisterHoursMenuView;
import dtu.group5.frontend.view.registerHours.RegisterWorkedHoursView;
import dtu.group5.frontend.view.session.SessionInformationView;
import dtu.group5.frontend.view.session.SessionLoginView;
import dtu.group5.frontend.view.session.SessionLogoutView;
import dtu.group5.frontend.view.session.SessionMenuView;

import java.util.List;

// Made by Matthias(s245759)
public class Main {
  public static void main(String[] args) {
    // Retrieve data from json files
    new DataManager(); // Load datamanager
    List<Coworker> coworkers = DataManager.getInstance().loadCoworkers();
    List<BaseActivity> activities = DataManager.getInstance().loadFixedActivities();
    activities.addAll(DataManager.getInstance().loadProjectActivities());
    List<Project> projects = DataManager.getInstance().loadProjects();

    // Initialize repositories
    ActivityRepository.initialize(activities);
    ProjectRepository.initialize(projects);
    CoworkerRepository.initialize(coworkers);

    // Services
    CoworkerService coworkerService = new CoworkerService();
    ProjectService projectService = new ProjectService();
    AssignmentService assignmentService = new AssignmentService(coworkerService);
    ActivityService activityService = new ActivityService(assignmentService, coworkerService);
    WorkedHoursService workedHoursService = new WorkedHoursService();

    // Controllers
    ActivityController activityController = new ActivityController(activityService);
    CoworkerController coworkerController = new CoworkerController(coworkerService);
    ProjectController projectController = new ProjectController(projectService);
    AssignmentController assignmentController = new AssignmentController(assignmentService);
    WorkedHoursController workedHoursController = new WorkedHoursController(workedHoursService);

    // Coworker views
    ICreateView<Coworker> coworkerCreateView = new CoworkerCreateView(coworkerController);
    IListView<Coworker> coworkerListView = new CoworkerListView(coworkerController);
    IEditView<Coworker> coworkerEditView = new CoworkerEditView(coworkerController);
    IDeleteView coworkerDeleteView = new CoworkerDeleteView(coworkerController);
    IView coworkerMenuView = new CoworkerMenuView(coworkerController, coworkerCreateView, coworkerListView, coworkerEditView, coworkerDeleteView);

    // Project views
    ICreateView<Project> projectCreateView = new ProjectCreateView(projectController);
    IListView<Project> projectListView = new ProjectListView(projectController);
    IEditView<Project> projectEditView = new ProjectEditView(projectController);
    IEditView<Project> projectAddMemberView = new ProjectAddMemberView(projectController, coworkerController, assignmentController);
    IEditView<Project> projectRemoveMemberView = new ProjectRemoveMemberView(projectController, coworkerController);
    IListView<Project> projectMemberListView = new ProjectListMembersView(projectController);
    IListView<Project> projectActivityListView = new ProjectListActivitiesView(projectController);
    IReportView projectReportView = new ProjectReportView(projectController);
    IDeleteView projectDeleteView = new ProjectDeleteView(projectController);
    IListView<Project> projectActivityAvailableCoworkers = new ProjectActivityAvailableCoworkersView(projectController, activityController );
    IView projectMenuView = new ProjectMenuView(projectController, projectCreateView, projectListView, projectEditView, projectAddMemberView, projectRemoveMemberView, projectMemberListView, projectActivityListView, projectReportView, projectDeleteView, projectActivityAvailableCoworkers);

    // Activity views
    ICreateView<BaseActivity> activityCreateView = new ActivityCreateView(activityController, coworkerController);

    IListView<FixedActivity> activityFixedListView = new ActivityListFixedView(activityController);
    IListView<ProjectActivity> activityProjectListView = new ActivityListProjectView(activityController);
    IView activityListMenuView = new ActivityListMenuView(activityFixedListView, activityProjectListView);

    IEditView<BaseActivity> activityEditView = new ActivityEditView(activityController);
    IEditView<BaseActivity> activityAssignView = new ActivityAssignCoworkerView(activityController, coworkerController, assignmentController);
    IListView<BaseActivity> activityMemberListView = new ActivityListMembersView(activityController);
    IDeleteView activityDeleteView = new ActivityDeleteView(activityController);
    IView activityMenuView = new ActivityMenuView(activityController, activityCreateView, activityListMenuView, activityEditView, activityAssignView, activityMemberListView, activityDeleteView);

    // Session views
    IView sessionLoginView = new SessionLoginView();
    IView sessionLogoutView = new SessionLogoutView();
    IView sessionInfoView = new SessionInformationView();
    IView sessionMenuView = new SessionMenuView(sessionLoginView, sessionLogoutView, sessionInfoView);

    // Register hours views
    IView registerWorkedHoursView = new RegisterWorkedHoursView(workedHoursController);
    IView editWorkedHoursView = new EditWorkedHoursView(workedHoursController);
    IView deleteWorkedHoursView = new DeleteWorkedHoursView(workedHoursController);
    IView listTodaysRegisteredHoursView = new ListTodaysRegisteredHoursView(workedHoursController);
    IView listUnregisteredActivitiesView = new ListUnregisteredActivitiesView(workedHoursController);
    IView registerHoursMenuView = new RegisterHoursMenuView(registerWorkedHoursView, editWorkedHoursView, deleteWorkedHoursView, listTodaysRegisteredHoursView, listUnregisteredActivitiesView);

    // Main menu
    IView mainMenu = new MainMenuView(projectMenuView, activityMenuView, coworkerMenuView, sessionMenuView, registerHoursMenuView);

    // Main Login menu
    IView mainLoginMenu = new LoginMenuView(mainMenu, sessionLoginView, coworkerCreateView);

    // Show main menu
    if (!Session.getInstance().isLoggedIn())
      mainLoginMenu.runView();
    else
      mainMenu.runView();
  }
}

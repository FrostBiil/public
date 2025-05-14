package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.frontend.controller.ActivityController;
import dtu.group5.frontend.controller.ProjectController;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IListView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ProjectActivityAvailableCoworkersView implements IListView<Project> {
    private final ProjectController projectController;
    private final ActivityController activityController;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    // Made By Elias(241121)
    public ProjectActivityAvailableCoworkersView(ProjectController projectController, ActivityController activityController) {
        this.projectController = projectController;
        this.activityController = activityController;
    }
    // Made By Elias(241121)
    @Override
    public List<Project> getList() {
        return List.of();
    }

    // Made By Elias(241121)
    @Override
    public void runView() {
        findAvailableCoworkers();
    }

    // Made By Elias(241121)
    public void findAvailableCoworkers() {
        printer.printHeader("AVAILABLE COWORKERS");

        if (!Session.getInstance().isLoggedIn()) {
            printer.printError("You must be logged in to see available coworkers for an activity.");
            return;
        }

        // Select project and activity
        int projectNumber = input.prompt(
                "Enter project number to see avaliable coworkers",
                Integer::parseInt,
                val -> val >= 1,
                "Invalid project number",
                false
        );

        Optional<Project> project = projectController.getList().stream()
                .filter(p -> p.getProjectNumber() == projectNumber)
                .findFirst();

        if (project.isEmpty()) {
            printer.printError("Project not found");
            return;
        }

        if (project.get().getProjectLeader() != null) {
            if (!Session.getInstance().getLoggedInUser().getInitials().equals(project.get().getProjectLeader().getInitials())) {
                printer.printError("You are not the project leader and cannot see available coworkers for activity");
                return;
            }
        } else {
            if (project.get().getCoworkers().stream().noneMatch(c -> c.getInitials().equalsIgnoreCase(Session.getInstance().getLoggedInUser().getInitials()))) {
                printer.printError("You are not assigned to this project and cannot see available coworkers for activity");
                return;
            }
        }

        // Select Activity
        String title = input.prompt(
                "Enter activity title to view",
                s -> s,
                s -> !s.isBlank(),
                "Title cannot be empty",
                false
        );

        BaseActivity activity = activityController.getList().stream()
                .filter(a -> a.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
        if (activity == null) {
            printer.printError("Activity not found");
            return;
        }
        ProjectActivity projectActivity = null;
        List<Coworker> availableCoworkers = null;
        if (activity instanceof ProjectActivity) {
            projectActivity = (ProjectActivity) activity;
            availableCoworkers = activityController.listAvailableCoworkers(projectActivity);
            // Proceed with projectActivity
        } else {
            printer.printError("Activity is not a ProjectActivity");
        }

        List<String> coworkerStrings = availableCoworkers.stream()
                .map(Coworker::getName)
                .collect(Collectors.toList());

        printer.printHeader("AVAILABLE COWORKERS");
        printer.printList(coworkerStrings);
        input.waitForEnter();
    }
}

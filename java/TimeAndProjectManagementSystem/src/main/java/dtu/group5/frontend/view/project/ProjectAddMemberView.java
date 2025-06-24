package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.frontend.controller.AssignmentController;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.controller.ProjectController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IEditView;

import java.util.Optional;

public class ProjectAddMemberView implements IEditView<Project> {
    private final ProjectController projectController;
    private final Controller<Coworker> coworkerController;
    private final AssignmentController assignmentController;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    public ProjectAddMemberView(Controller<Project> projectController, Controller<Coworker> coworkerController, AssignmentController assignmentController) {
      this.assignmentController = assignmentController;
      // Get ProjectController to use specific methods
        if (projectController instanceof ProjectController pController) {
            this.projectController = pController;
        } else throw new IllegalArgumentException("ProjectController.java expected for Project Controller");
        this.coworkerController = coworkerController;
    }

    @Override
    public void runView() {
        edit();
    }

    @Override
    public Project edit() {
        printer.printHeader("ADD PROJECT MEMBER");

        // Select project and activity
        int projectNumber = input.prompt(
                "Enter project number to add member too",
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
            return null;
        }

        // Select cowker to add
        String coworkerInitials = input.prompt(
                "Enter coworker initials to add",
                s -> s,
                s -> !s.isBlank(),
                "Initials cannot be empty",
                false
        );

        Coworker coworker = coworkerController.getList().stream()
                .filter(c -> c.getInitials().equalsIgnoreCase(coworkerInitials))
                .findFirst()
                .orElse(null);

        if (coworker == null) {
            printer.printError("Coworker not found");
            return null;
        }

        Optional<String> response = assignmentController.assignCoworkerToProject(project.get(), coworker);
        if (response.isPresent()) {
            printer.printError(response.get());
            return null;
        }

        printer.printSuccess("Project member successfully added!");
        return project.get();
    }
}

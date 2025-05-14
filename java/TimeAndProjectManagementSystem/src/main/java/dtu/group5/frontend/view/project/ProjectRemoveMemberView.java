package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.controller.ProjectController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IEditView;

import java.util.Optional;

// Made by Elias (241121)
public class ProjectRemoveMemberView implements IEditView<Project> {
    private final ProjectController projectController;
    private final Controller<Coworker> coworkerController;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    //Made by Elias (241121)
    public ProjectRemoveMemberView(Controller<Project> projectController, Controller<Coworker> coworkerController) {
        // Get ProjectController to use specific methods
        if (projectController instanceof ProjectController pController) {
            this.projectController = pController;
        } else throw new IllegalArgumentException("ProjectController.java expected for Project Controller");
        this.coworkerController = coworkerController;
    }

    // Made by Elias (241121)
    @Override
    public void runView() {
        edit();
    }

    // Made by Elias (241121)
    @Override
    public Project edit() {
        printer.printHeader("REMOVE PROJECT MEMBER");

        // Select project and activity
        int projectNumber = input.prompt(
                "Enter project number to remove member from",
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
                "Enter coworker initials to remove",
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

        Optional<String> response = projectController.removeCoworkerFromProject(project.get(), coworker);
        if (response.isPresent()) {
            printer.printError(response.get());
            return null;
        }

        printer.printSuccess("Project member successfully removed!");
        return project.get();
    }
}

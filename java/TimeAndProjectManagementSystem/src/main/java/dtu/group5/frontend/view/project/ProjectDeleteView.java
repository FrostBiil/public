package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Project;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConfirmEnum;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IDeleteView;

import java.util.Optional;

public class ProjectDeleteView implements IDeleteView {

    private final Controller<Project> controller;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    public ProjectDeleteView(Controller<Project> controller) {
        this.controller = controller;
    }

    @Override
    public void runView() {
        delete();
    }

    @Override
    public void delete() {
        printer.printHeader("DELETE PROJECT");

        if (!Session.getInstance().isLoggedIn()) {
            printer.printError("You must be logged in to delete a project");
            return;
        }

        // Select project and activity
        int projectNumber = input.prompt(
                "Enter project number to delete",
                Integer::parseInt,
                val -> val >= 1,
                "Invalid project number",
                false
        );

        Optional<Project> project = controller.getList().stream()
                .filter(p -> p.getProjectNumber() == projectNumber)
                .findFirst();

        if (project.isEmpty()) {
            printer.printError("Project not found");
            return;
        }


        if (project.get().getProjectLeader() != null) {
            if (!Session.getInstance().getLoggedInUser().getInitials().equals(project.get().getProjectLeader().getInitials())) {
                printer.printError("You are not the project leader and cannot delete this project");
                return;
            }
        }

        ConfirmEnum type = input.promptEnum("Are you sure that you want to delete project with project number " + project.get().getProjectNumber(), ConfirmEnum.class, false);

        if (type == ConfirmEnum.CANCEL) {
            printer.printInfo("Project deletion cancelled.");
            return;
        }

        // Delete coworker
        Optional<String> response = controller.handleDelete(project.get());
        if (response.isPresent()) {
            printer.printError(response.get());
            return;
        }

        printer.printSuccess("Project deleted successfully!");
    }
}

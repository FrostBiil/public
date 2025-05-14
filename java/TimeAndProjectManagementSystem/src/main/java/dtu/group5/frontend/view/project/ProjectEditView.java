package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectType;
import dtu.group5.backend.util.DateUtil;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IEditView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Made by Elias(241121)
public class ProjectEditView implements IEditView<Project> {
    private final Controller<Project> controller;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    // Made By Elias(241121)
    public ProjectEditView(Controller<Project> controller) {
        this.controller = controller;
    }

    // Made By Elias(241121)
    @Override
    public void runView() {
        edit();
    }
    // Made By Elias(241121)
    @Override
    public Project edit() {
        printer.printHeader("EDIT PROJECT");

        if (!Session.getInstance().isLoggedIn()) {
            printer.printError("You must be logged in to edit a project");
            return null;
        }

        // Select project and activity
        int projectNumber = input.prompt(
                "Enter project number to edit",
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
            return null;
        }


        if (project.get().getProjectLeader() != null) {
            if (!Session.getInstance().getLoggedInUser().getInitials().equals(project.get().getProjectLeader().getInitials())) {
                printer.printError("You are not the project leader and cannot edit this project");
                return null;
            }
        } else {
            if (project.get().getCoworkers().stream().noneMatch(c ->  c.getInitials().equalsIgnoreCase(Session.getInstance().getLoggedInUser().getInitials()))) {
                printer.printError("You are not assigned to this project and cannot edit it");
                return null;
            }
        }

        // Optional edits
        printer.printInfo("Select fields to update. Leave blank to keep current value.");
        String newTitle = input.prompt("New title", s -> s, v -> true, null, true);
        String newDescription = input.prompt("New description", s -> s, v -> true, null, true);
        Date newStart = input.prompt("New start date (dd-MM-yyyy)", DateUtil::parseDate, null, "Invalid date format", true);
        Date newEnd = input.prompt("New end date (dd-MM-yyyy)", DateUtil::parseDate, null, "Invalid date format", true);
        String newProjectLeaderInitials = input.prompt("New project leader initials", s -> s, v -> true, null, true);
        ProjectType type = input.promptEnum("Project type", ProjectType.class, true);

        // Apply changes
        Map<String, Object> changes = new HashMap<>();
        if (newTitle != null && !newTitle.isBlank()) changes.put("title", newTitle);
        if (newDescription != null && !newDescription.isBlank()) changes.put("description", newDescription);
        if (newStart != null) changes.put("startDate", newStart);
        if (newEnd != null) changes.put("endDate", newEnd);
        if (newProjectLeaderInitials != null && !newProjectLeaderInitials.isBlank()) changes.put("project-leader", newProjectLeaderInitials);
        if (type != null) changes.put("type", type);

        Optional<String> response = controller.handleEdit(project.get(), changes);
        if (response.isPresent()) {
            printer.printError(response.get());
            return null;
        }

        printer.printSuccess("Project updated successfully!");

        return project.get();
    }
}
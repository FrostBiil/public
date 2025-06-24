package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectListMembersView implements IListView<Project> {
    private final Controller<Project> controller;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();


    public ProjectListMembersView(Controller<Project> controller) {
        this.controller = controller;
    }

    @Override
    public void runView() {
        printer.printHeader("PROJECT MEMBER LIST");

        // Select project and activity
        int projectNumber = input.prompt(
                "Enter project number to view",
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

        if (project.get().getCoworkers().isEmpty()) {
            printer.printError("No coworkers found for this project");
            return;
        }

        List<String[]> tableRows = new ArrayList<>();
        tableRows.add(new String[]{"initial", "name", "Is project leader"});

        for (Coworker c : project.get().getCoworkers()) {
            tableRows.add(new String[]{
                    c.getInitials(),
                    c.getName(),
                    project.get().getProjectLeader() != null ? (c.getInitials().equals(project.get().getProjectLeader().getInitials()) ? "Yes" : "No") : "No"
            });
        }
        printer.printTable(tableRows);
        input.waitForEnter();
    }

    @Override
    public List<Project> getList() {
        return controller.getList();
    }
}

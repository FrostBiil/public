package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.frontend.controller.ProjectController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// Made By Elias(241121)
public class ProjectListActivitiesView implements IListView<Project> {
    private final ProjectController controller;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    // Made By Elias(241121)
    public ProjectListActivitiesView(ProjectController controller) {
        this.controller = controller;
    }

    // Made By Elias(241121)
    @Override
    public void runView() {
        printer.printHeader("PROJECT ACTIVITY LIST");

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

        Set<ProjectActivity> activities = controller.getProjectActivities(project.get());
        if (activities.isEmpty()) {
            printer.printError("No activities found for this project");
            return;
        }

        List<String[]> tableRows = new ArrayList<>();
        tableRows.add(new String[]{"title", "description"});

        for (ProjectActivity a : activities) {
            tableRows.add(new String[]{
                a.getTitle(),
                a.getDescription()
            });
        }
        printer.printTable(tableRows);
        input.waitForEnter();
    }

    // Made By Elias(241121)
    @Override
    public List<Project> getList() {
        return controller.getList();
    }
}

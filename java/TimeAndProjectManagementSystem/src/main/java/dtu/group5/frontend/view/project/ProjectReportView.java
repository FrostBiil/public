package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Project;
import dtu.group5.frontend.controller.ProjectController;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IReportView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


public class ProjectReportView implements IReportView {

    private final ProjectController projectController;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public ProjectReportView(ProjectController projectController) {
        this.projectController = projectController;
    }

    @Override
    public void runView() {
        this.showReport();
    }

    @Override
    public void showReport() {
        printer.printHeader("PROJECT WORK REPORT");

        if (!Session.getInstance().isLoggedIn()) {
            printer.printError("You must be logged in to see a project work report");
            return;
        }

        // Select project and activity
        int projectNumber = input.prompt(
                "Enter project number to see work report",
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
                printer.printError("You are not the project leader and cannot see project work report");
                return;
            }
        } else {
            if (project.get().getCoworkers().stream().noneMatch(c ->  c.getInitials().equalsIgnoreCase(Session.getInstance().getLoggedInUser().getInitials()))) {
                printer.printError("You are not assigned to this project and cannot see project work report");
                return;
            }
        }

        List<String[]> report = List.of();

        try {
             report = projectController.generateWorkloadReport(project.get());
        } catch (IllegalArgumentException e) {
            printer.printError(e.getMessage());
        }

        if (report.isEmpty()) {
            if (projectController.getProjectActivities(project.get()).isEmpty()) {
                printer.printError("No activities found for this project. Therefore no project work report can be generated.");
                return;
            }
            printer.printError("No project work report found.");
            return;
        }



        printer.printHeader("Project work report:");
        printer.printInfo("Activities:");
        printer.printTable(report);

        printer.printInfo("Project Number: " + (project.get().getProjectNumber() > 0 ? project.get().getProjectNumber() : "None"));
        printer.printInfo("Project Title: " + project.get().getTitle());
        printer.printInfo("Project Leader: " + (project.get().getProjectLeader() != null ? project.get().getProjectLeader().getName() : "No project leader assigned"));
        printer.printInfo("Project Members: " + project.get().getCoworkers().size());
        printer.printInfo("Project Start Date: " + (project.get().getStartDate() != null ? dateFormat.format(project.get().getStartDate()) : "No start date set"));
        printer.printInfo("Project End Date: " + (project.get().getEndDate() != null ? dateFormat.format(project.get().getEndDate()) : "No end date set"));
        printer.printInfo("Project Status: " + project.get().getStatus().getFormattedName());
        printer.printInfo("Project Type: " + project.get().getType().getFormattedName());

        input.waitForEnter();
    }
}

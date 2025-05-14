package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Project;
import dtu.group5.frontend.controller.ProjectController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// Made By Mattias (s245759)
public class ProjectListView implements IListView<Project> {
  private final ProjectController controller;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public ProjectListView(ProjectController controller) {
    this.controller = controller;
  }

  // Made By Mattias (s245759)
  @Override
  public void runView() {
    printer.printHeader("PROJECT LIST");

    List<Project> projectList = this.getList();

    if (projectList.isEmpty()) {
      printer.printInfo("No projects available.");
      return;
    }

    List<String[]> tableRows = new ArrayList<>();
    tableRows.add(new String[]{"Number", "Title", "Description", "Leader", "Start", "End", "Members", "Activities", "Type"});

    for (Project p : projectList) {
        tableRows.add(new String[]{
            String.valueOf(p.getProjectNumber()),
            p.getTitle(),
            p.getDescription() != null ? p.getDescription() : "None",
            p.getProjectLeader() != null ? p.getProjectLeader().getName() : "None",
            p.getStartDate() != null ? dateFormat.format(p.getStartDate()) : "None",
            p.getEndDate() != null ? dateFormat.format(p.getEndDate()) : "None",
            String.valueOf(p.getCoworkers().size()),
            String.valueOf(controller.getProjectActivities(p).size()),
            p.getType().getFormattedName()
        });
    }
    printer.printTable(tableRows);
    input.waitForEnter();
  }
  // Made by Elias(241121)
  @Override
  public List<Project> getList() {
    return controller.getList();
  }
}

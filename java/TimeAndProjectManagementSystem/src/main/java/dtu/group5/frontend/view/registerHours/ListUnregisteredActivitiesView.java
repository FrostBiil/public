package dtu.group5.frontend.view.registerHours;

import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.frontend.controller.WorkedHoursController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IView;

import java.util.ArrayList;
import java.util.List;

public class ListUnregisteredActivitiesView implements IView {
  private final WorkedHoursController workedHoursController;
  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public ListUnregisteredActivitiesView(WorkedHoursController workedHoursController) {
    this.workedHoursController = workedHoursController;
  }

  @Override
  public void runView() {
    printer.printHeader("UNREGISTERED ACTIVITIES FOR TODAY");

    String initials = input.prompt(
      "Enter your initials",
      s -> s,
      s -> !s.isBlank(),
      "Initials cannot be empty",
      false
    );

    List<ProjectActivity> activities = workedHoursController.listUnregisteredActivitiesToday(initials);

    if (activities.isEmpty()) {
      printer.printInfo("All today's activities have registered hours.");
    } else {
      List<String[]> rows = new ArrayList<>();
      rows.add(new String[]{"Project #", "Activity Title", "Description"});

      for (ProjectActivity a : activities) {
        rows.add(new String[]{
          String.valueOf(a.getProjectNumber()),
          a.getTitle(),
          a.getDescription()
        });
      }

      printer.printTable(rows);
    }

    input.waitForEnter();
  }
}

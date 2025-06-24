package dtu.group5.frontend.view.registerHours;

import dtu.group5.backend.util.DateUtil;
import dtu.group5.frontend.controller.WorkedHoursController;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IView;

import java.util.Date;
import java.util.Optional;

public class DeleteWorkedHoursView implements IView {
  private final WorkedHoursController workedHoursController;
  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public DeleteWorkedHoursView(WorkedHoursController workedHoursController) {
    this.workedHoursController = workedHoursController;
  }

  @Override
  public void runView() {
    printer.printHeader("DELETE WORKED HOURS");

    if (!Session.getInstance().isLoggedIn()) {
      printer.printError("You must be logged in to delete worked hours.");
      return;
    }

    int projectNumber = input.prompt(
      "Enter project number",
      Integer::parseInt,
      val -> val > 0,
      "Project number must be positive",
      false
    );

    String activityTitle = input.prompt(
      "Enter activity title",
      s -> s,
      s -> !s.isBlank(),
      "Activity title cannot be empty",
      false
    );

    Date date = input.prompt(
      "Enter date to delete hours for (dd-MM-yyyy)",
      DateUtil::parseDate,
      d -> true,
      "Invalid date format",
      false
    );

    Optional<String> result = workedHoursController.deleteWorkedHours(projectNumber, activityTitle, date);

    if (result.isPresent()) {
      printer.printError(result.get());
    } else {
      printer.printSuccess("Worked hours successfully deleted.");
    }

    input.waitForEnter();
  }
}

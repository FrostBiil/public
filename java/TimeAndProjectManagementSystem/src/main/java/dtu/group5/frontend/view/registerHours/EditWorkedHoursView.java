package dtu.group5.frontend.view.registerHours;

import dtu.group5.backend.util.DateUtil;
import dtu.group5.frontend.controller.WorkedHoursController;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IView;

import java.util.Date;
import java.util.Optional;

public class EditWorkedHoursView implements IView {
  private final WorkedHoursController workedHoursController;
  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public EditWorkedHoursView(WorkedHoursController workedHoursController) {
    this.workedHoursController = workedHoursController;
  }

  @Override
  public void runView() {
    printer.printHeader("EDIT WORKED HOURS");

    if (!Session.getInstance().isLoggedIn()) {
      printer.printError("You must be logged in to edit worked hours.");
      return;
    }

    String initials = Session.getInstance().getLoggedInUser().getInitials();

    int projectNumber = input.prompt(
      "Enter project number",
      Integer::parseInt,
      val -> val > 0,
      "Invalid project number",
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
      "Enter date to edit (dd-MM-yyyy)",
      DateUtil::parseDate,
      d -> true,
      "Invalid date format",
      false
    );

    double hours = input.prompt(
      "Enter new worked hours",
      Double::parseDouble,
      val -> val >= 0,
      "Hours must be non-negative",
      false
    );

    Optional<String> result = workedHoursController.editWorkedHours(projectNumber, activityTitle, initials, "workedHours", hours, date);

    if (result.isPresent()) {
      printer.printError(result.get());
    } else {
      printer.printSuccess("Worked hours updated successfully.");
    }

    input.waitForEnter();
  }
}

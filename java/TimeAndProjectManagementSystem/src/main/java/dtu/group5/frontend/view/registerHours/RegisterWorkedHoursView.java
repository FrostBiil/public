package dtu.group5.frontend.view.registerHours;

import dtu.group5.frontend.controller.WorkedHoursController;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IView;

import java.util.Optional;

  // Made By Matthias (s245759)
public class RegisterWorkedHoursView implements IView {
  private final WorkedHoursController workedHoursController;
  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public RegisterWorkedHoursView(WorkedHoursController workedHoursController) {
    this.workedHoursController = workedHoursController;
  }

  // Made By Matthias (s245759)
  @Override
  public void runView() {
    printer.printHeader("REGISTER WORKED HOURS");

    if (!Session.getInstance().isLoggedIn()) {
      printer.printError("You must be logged in to register hours.");
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

    double hours = input.prompt(
      "Enter hours worked",
      Double::parseDouble,
      val -> val > 0,
      "Hours must be positive and in 0.5 increments",
      false
    );

    Optional<String> result = workedHoursController.registerWorkedHours(projectNumber, activityTitle, hours);

    if (result.isPresent()) {
      printer.printError(result.get());
    } else {
      printer.printSuccess("Worked hours registered successfully!");
    }

    input.waitForEnter();
  }
}

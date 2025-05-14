package dtu.group5.frontend.view.registerHours;

import dtu.group5.backend.util.DateUtil;
import dtu.group5.frontend.controller.WorkedHoursController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IView;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// Made By Matthias (s245759)
public class ListTodaysRegisteredHoursView implements IView {
  private final WorkedHoursController workedHoursController;
  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public ListTodaysRegisteredHoursView(WorkedHoursController workedHoursController) {
    this.workedHoursController = workedHoursController;
  }
  // Made By Matthias (s245759)
  @Override
  public void runView() {
    printer.printHeader("LIST REGISTERED WORKED HOURS BY DATE");

    String initials = input.prompt(
      "Please enter initials",
      s -> s,
      s -> !s.isBlank(),
      "Initials cannot be empty",
      false
    );

    Date date = input.prompt(
      "Enter date to view hours for (dd-MM-yyyy)",
      DateUtil::parseDate,
      d -> true,
      "Invalid date format",
      false
    );

    List<String[]> rows = new LinkedList<>(workedHoursController.getWorkedHoursByCoworker(initials, date));

    if (rows.isEmpty()) {
      printer.printInfo("No worked hours registered today.");
    } else {
      rows.addFirst(new String[]{"Project #", "Activity Title", "Hours", "Date"});
      printer.printTable(rows);
    }

    input.waitForEnter();
  }
}

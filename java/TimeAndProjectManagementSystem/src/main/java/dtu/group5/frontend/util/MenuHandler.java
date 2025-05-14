package dtu.group5.frontend.util;

import java.util.List;

// Made by Matthias (s245759)
public class MenuHandler {
  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  // Made by Matthias (s245759)
  public void runMenu(String title, List<MenuOption> options) {
    while (true) {
      printer.printHeader(title);

      for (int i = 0; i < options.size(); i++) {
        printer.printOption(i, options.get(i).getLabel()); // 0-based visning
      }

      int choice = input.prompt(
        "Select option",
        Integer::parseInt,
        val -> val >= 0 && val < options.size(),
        "Please enter a number between 0 and " + (options.size() - 1),
        false
      );

      MenuOption selected = options.get(choice);

      // Hvis 'Back' er valgt (typisk label 'Back' på 0), afslut menuen
      if (selected.getLabel().equalsIgnoreCase("Back")) return;

      selected.execute();
    }
  }
}

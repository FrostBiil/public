package dtu.group5.frontend.util;

import dtu.group5.frontend.view.IView;

import java.util.List;
import java.util.function.BooleanSupplier;

public class RedirectMenuHandler {
    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    private final IView redirectOption;
    private final BooleanSupplier condition;

    public RedirectMenuHandler(IView redirectOption, BooleanSupplier condition) {
        this.redirectOption = redirectOption;
        this.condition = condition;
    }

    public void runMenu(String title, List<MenuOption> options) {
        while (true) {
            if (condition.getAsBoolean()) {
                redirectOption.runView();
                return;
            }

            printer.printHeader(title);

            for (int i = 0; i < options.size(); i++) {
                printer.printOption(i, options.get(i).getLabel());
            }

            int choice = input.prompt(
                    "Select option",
                    Integer::parseInt,
                    val -> val >= 0 && val < options.size(),
                    "Please enter a number between 0 and " + (options.size() - 1),
                    false
            );

            MenuOption selected = options.get(choice);

            if (selected.getLabel().equalsIgnoreCase("Back")) return;

            selected.execute();
        }
    }
}
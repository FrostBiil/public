package dtu.group5.frontend.view.coworker;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IEditView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Made By Elias(s241121)
public class CoworkerEditView implements IEditView<Coworker> {
    private final Controller<Coworker> controller;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    public CoworkerEditView(Controller<Coworker> controller) {
        this.controller = controller;
    }

    // Made By Elias(s241121)
    @Override
    public void runView() {
        edit();
    }

    // Made By Elias(s241121)
    @Override
    public Coworker edit() {
        printer.printHeader("EDIT COWORKER");

        // Select cowker to edit
        String coworkerInitials = input.prompt(
                "Enter coworker initials to edit",
                s -> s,
                s -> !s.isBlank(),
                "Initials cannot be empty",
                false
        );

        Coworker coworker = controller.getList().stream()
                .filter(c -> c.getInitials().equalsIgnoreCase(coworkerInitials))
                .findFirst()
                .orElse(null);

        if (coworker == null) {
            printer.printError("Coworker not found");
            return null;
        }

        // Optional edits
        printer.printInfo("Select fields to update. Leave blank to keep current value.");

        String newName = input.prompt("New name", s -> s, v -> true, null, true);

        // Apply changes
        Map<String, Object> changes = new HashMap<>();
        if (newName != null && !newName.isBlank()) changes.put("name", newName);

        Optional<String> response = controller.handleEdit(coworker, changes);
        if (response.isPresent()) {
            printer.printError(response.get());
            return null;
        }

        printer.printSuccess("Coworker updated successfully!");

        return coworker;
    }
}

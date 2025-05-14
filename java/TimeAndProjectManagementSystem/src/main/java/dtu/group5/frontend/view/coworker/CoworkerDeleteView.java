package dtu.group5.frontend.view.coworker;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConfirmEnum;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IDeleteView;

import java.util.Optional;

// Made By Elias(s241121)
public class CoworkerDeleteView implements IDeleteView {

    private final Controller<Coworker> controller;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    public CoworkerDeleteView(Controller<Coworker> controller) {
        this.controller = controller;
    }

    // Made By Elias(s241121)
    @Override
    public void runView() {
        delete();
    }


    // Made By Elias(s241121)
    @Override
    public void delete() {
        printer.printHeader("DELETE COWORKER");

        // Select cowker to edit
        String coworkerInitials = input.prompt(
                "Enter coworker initials to delete",
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
            return;
        }

        ConfirmEnum type = input.promptEnum("Are you sure that you want to delete coworker with initials " + coworker.getInitials(), ConfirmEnum.class, false);

        if (type == ConfirmEnum.CANCEL) {
            printer.printInfo("Coworker deletion cancelled.");
            return;
        }

        // Delete coworker
        Optional<String> response = controller.handleDelete(coworker);
        if (response.isPresent()) {
            printer.printError(response.get());
            return;
        }

        printer.printSuccess("Coworker deleted successfully!");
    }
}

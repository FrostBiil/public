package dtu.group5.frontend.view.activity;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConfirmEnum;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IDeleteView;

import java.util.Optional;

public class ActivityDeleteView implements IDeleteView {

    private final Controller<BaseActivity> controller;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    public ActivityDeleteView(Controller<BaseActivity> controller) {
        this.controller = controller;
    }

    @Override
    public void runView() {
        delete();
    }


    @Override
    public void delete() {
        printer.printHeader("DELETE ACTIVITY");

        String title = input.prompt(
                "Enter activity title to delete",
                s -> s,
                s -> !s.isBlank(),
                "Title cannot be empty",
                false
        );


        BaseActivity activity = controller.getList().stream()
                .filter(a -> a.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (activity == null) {
            printer.printError("Activity not found");
            return;
        }

        ConfirmEnum type = input.promptEnum("Are you sure that you want to delete activity with title " + activity.getTitle(), ConfirmEnum.class, false);

        if (type == ConfirmEnum.CANCEL) {
            printer.printInfo("Activity deletion cancelled.");
            return;
        }

        // Delete coworker
        Optional<String> response = controller.handleDelete(activity);
        if (response.isPresent()) {
            printer.printError(response.get());
            return;
        }

        printer.printSuccess("Activity deleted successfully!");
    }
}

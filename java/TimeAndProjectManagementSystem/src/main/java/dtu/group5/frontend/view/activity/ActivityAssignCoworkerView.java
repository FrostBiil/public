package dtu.group5.frontend.view.activity;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.frontend.controller.ActivityController;
import dtu.group5.frontend.controller.AssignmentController;
import dtu.group5.frontend.controller.CoworkerController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IEditView;

import java.util.Optional;

// Made by Elias (241121)
public class ActivityAssignCoworkerView implements IEditView<BaseActivity> {
    private final ActivityController activityController;
    private final CoworkerController coworkerController;
    private final AssignmentController assignmentController;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    // Made by Elias (241121)
    public ActivityAssignCoworkerView(ActivityController activityController, CoworkerController coworkerController, AssignmentController assignmentController) {
        this.activityController = activityController;
        this.coworkerController = coworkerController;
      this.assignmentController = assignmentController;
    }

    // Made by Elias (241121)
    @Override
    public void runView() {
        edit();
    }

    // Made by Elias (241121)
    @Override
    public BaseActivity edit() {
        printer.printHeader("ADD ACTIVITY MEMBER");

        String title = input.prompt(
                "Enter activity title to add member to",
                s -> s,
                s -> !s.isBlank(),
                "Title cannot be empty",
                false
        );

        BaseActivity activity = activityController.getList().stream().filter(a -> a.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
        if (activity == null) {
            printer.printError("Activity not found");
            return null;
        }


        if (activity instanceof FixedActivity) {
            printer.printError("Cannot add members to fixed activities");
            return activity;
        } else if (activity instanceof ProjectActivity projectActivity) {
            // Select coworker to add
            String coworkerInitials = input.prompt(
                    "Enter coworker initials to add",
                    s -> s,
                    s -> !s.isBlank(),
                    "Initials cannot be empty",
                    false
            );

            Coworker coworker = coworkerController.getList().stream()
                    .filter(c -> c.getInitials().equalsIgnoreCase(coworkerInitials))
                    .findFirst()
                    .orElse(null);

            if (coworker == null) {
                printer.printError("Coworker not found");
                return null;
            }

            boolean force = input.prompt(
              "Force add coworker? (y/n)",
              s -> s.trim().toLowerCase(),
              s -> s.equals("y") || s.equals("n"),
              "Please enter 'y' or 'n'",
              false
            ).equals("y");

            Optional<String> responce = assignmentController.assignMember(projectActivity, coworker, force);

            if (responce.isPresent()) {
                printer.printError(responce.get());
                return null;
            }

            printer.printSuccess("Activity member successfully added!");
            return activity;
        }

        printer.printError("Unknown activity type");

        return activity;
    }
}

package dtu.group5.frontend.view.activity;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.util.DateUtil;
import dtu.group5.frontend.controller.ActivityController;
import dtu.group5.frontend.controller.CoworkerController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.ICreateView;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ActivityCreateView implements ICreateView<BaseActivity> {
  private final ActivityController activityController;
  private final CoworkerController coworkerController;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public ActivityCreateView(ActivityController activityController, CoworkerController coworkerController) {
    this.activityController = activityController;
    this.coworkerController = coworkerController;
  }

  @Override
  public void runView() {
    create();
  }

  @Override
  public BaseActivity create() {
    printer.printHeader("CREATE ACTIVITY");

    String title = input.prompt(
    "Enter activity title (unique)",
    s -> s,
    s -> !s.isBlank(),
    "Title cannot be empty",
    false
    );

    ActivityChooseType type = input.promptEnum("Activity type", ActivityChooseType.class, false);

    String description = input.prompt(
    "Enter activity description",
    s -> s,
    s -> !s.isBlank(),
    "Description cannot be empty",
    false
    );

    if (type == ActivityChooseType.FIXED) {
        Date start = input.prompt(
                "Start date (DD-MM-YYYY)",
                DateUtil::parseDate,
                null, // Validation is done in the parser
                "Invalid date format.",
                false
        );

        Date end = input.prompt(
                "End date (DD-MM-YYYY)",
                DateUtil::parseDate,
                null, // Validation is done in the parser
                "Invalid date format.",
                false
        );

      String coworkerInitials = input.prompt(
              "Enter coworker initials for activity",
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

      FixedActivity activity = new FixedActivity(title, false, description, start, end, coworker);
      // Create activity
      Optional<String> responce = activityController.handleCreate(activity);

      // Display responce
      if (responce.isPresent()) {
        printer.printError(responce.get());
        return null;
      }
      printer.printSuccess("Activity created successfully!");
      return activity;
    }

    // Prompt for activity details
    int projectNumber = input.prompt(
      "Enter project number",
      Integer::parseInt,
      val -> val > 0,
      "Project number must be > 0",
      false
    );

    double expectedHours = input.prompt(
      "Enter expected hours",
      Double::parseDouble,
      val -> val > 0,
      "Expected hours must be > 0",
      false
    );

    int startYear = input.prompt(
      "Enter last two digits of starting year (0-99)",
      Integer::parseInt,
      val -> val > 0 && val <= 99,
      "Start year must be > 0",
      false
    );

    int startWeekNumber = input.prompt(
      "Enter starting week number (1-52)",
      Integer::parseInt,
      val -> val > 0 && val <= 52,
      "Start week number must be > 0",
      false
    );

    int endYear = input.prompt(
      "Enter last two digits of ending year (0-99)",
      Integer::parseInt,
      val -> val > 0 && val <= 99,
      "End year must be > 0",
      false
    );


    int endWeekNumber = input.prompt(
      "Enter ending week number (1-52)",
      Integer::parseInt,
      val -> val > 0 && val <= 52,
      "End week number must be > 0",
      false
    );


    ProjectActivity activity = new ProjectActivity(projectNumber, title, expectedHours, false, description, Set.of(), startYear, startWeekNumber, endYear, endWeekNumber, Map.of());

    // Create activity
    Optional<String> responce = activityController.handleCreate(activity);

    // Display responce
    if (responce.isPresent()) {
      printer.printError(responce.get());
      return null;
    }
    printer.printSuccess("Activity created successfully!");
    return activity;
  }
}

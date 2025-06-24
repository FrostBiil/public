package dtu.group5.frontend.view.activity;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.util.DateUtil;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IEditView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActivityEditView implements IEditView<BaseActivity> {
  private final Controller<BaseActivity> activityController;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public ActivityEditView(Controller<BaseActivity> activityController) {
    this.activityController = activityController;
  }

  @Override
  public void runView() {
    edit();
  }

  @Override
  public BaseActivity edit() {
    printer.printHeader("EDIT ACTIVITY");


    String title = input.prompt(
            "Enter activity title to edit",
            s -> s,
            s -> !s.isBlank(),
            "Title cannot be empty",
            false
    );


    BaseActivity activity = activityController.getList().stream()
            .filter(a -> a.getTitle().equalsIgnoreCase(title))
            .findFirst()
            .orElse(null);

    if (activity == null) {
      printer.printError("Activity not found");
      return null;
    }

    // Map to store changes
    Map<String, Object> changes = new HashMap<>();

    // General fields

    Boolean finished = input.prompt("Mark as finished? ('y' or 'n')", s -> {
      if (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes")) return true;
      if (s.equalsIgnoreCase("n") || s.equalsIgnoreCase("no")) return false;
      throw new IllegalArgumentException("Enter y or n");
    }, null, "Enter y or n", true);

    if (finished != null) changes.put("finished", finished);

    String description = input.prompt(
            "Enter activity description",
            s -> s,
            s -> !s.isBlank(),
            "Description cannot be empty",
            true
    );

    if (description != null && !description.isBlank()) changes.put("description", description);


    // Check if the activity is a FixedActivity and get specific details
    if (activity instanceof FixedActivity fixedActvity) {
      Date newStart = input.prompt("New start date (dd-MM-yyyy)", DateUtil::parseDate, null, "Invalid date format", true);
      if (newStart != null) changes.put("startdate", newStart);
      Date newEnd = input.prompt("New end date (dd-MM-yyyy)", DateUtil::parseDate, null, "Invalid date format", true);
      if (newEnd != null) changes.put("enddate", newEnd);

      Optional<String> response = activityController.handleEdit(activity, changes);
      if (response.isPresent()) {
        printer.printError(response.get());
        return null;
      }

      printer.printSuccess("Activity updated successfully!");
      return fixedActvity;
    }


    // Check if the activity is a ProjectActivity and get specific details
    if (activity instanceof ProjectActivity projectActivity) {
      double expectedHours = input.prompt(
              "Enter expected hours",
              Double::parseDouble,
              val -> val >= 0,
              "Expected hours must be a positive number",
              true,
              -1.0
      );
      if (expectedHours >= 0) changes.put("expectedHours", expectedHours);

      int startYear = input.prompt(
              "Enter last two digits of starting year (0-99)",
              Integer::parseInt,
              val -> val >= 0 && val <= 99,
              "Start year must be > 0",
              true,
              -1
      );

      if (startYear >= 0) changes.put("startyear", startYear);

      int startWeekNumber = input.prompt(
              "Enter starting week number (1-52)",
              Integer::parseInt,
              val -> val > 0 && val <= 52,
              "Start week number must be > 0",
              true,
              -1
      );


      if (startWeekNumber > 0) changes.put("startweek", startWeekNumber);

      int endYear = input.prompt(
              "Enter last two digits of ending year (0-99)",
              Integer::parseInt,
              val -> val >= 0 && val <= 99,
              "End year must be > 0",
              true,
              -1
      );

      if (endYear >= 0) changes.put("endyear", endYear);


      int endWeekNumber = input.prompt(
              "Enter ending week number (1-52)",
              Integer::parseInt,
              val -> val > 0 && val <= 52,
              "End week number must be > 0",
              true,
              -1
      );

      if (endWeekNumber > 0) changes.put("endweek", endWeekNumber);

      Optional<String> response = activityController.handleEdit(activity, changes);
      if (response.isPresent()) {
        printer.printError(response.get());
        return null;
      }

      printer.printSuccess("Activity updated successfully!");
      return projectActivity;
    }

    return activity;
  }
}

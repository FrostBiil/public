package dtu.group5.frontend.view.activity;

import dtu.group5.backend.model.FixedActivity;
import dtu.group5.frontend.controller.ActivityController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActivityListFixedView implements IListView<FixedActivity> {

  private final ActivityController controller;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public ActivityListFixedView(ActivityController controller) {
    this.controller = controller;
  }

  @Override
  public void runView() {
    printer.printHeader("ACTIVITY LIST");

    List<FixedActivity> activityList = this.getList();

    if (activityList.isEmpty()) {
      printer.printInfo("No activities found.");
      return;
    }

    List<String[]> tableRows = new ArrayList<>();

    tableRows.add(new String[]{"Name", "Description", "Start", "End"});

    for (FixedActivity a : activityList) {
        tableRows.add(new String[]{
            a.getTitle(),
            a.getDescription() != null ? a.getDescription() : "None",
            dateFormat.format(a.getStartDate()),
            dateFormat.format(a.getEndDate()),
        });
    }
    printer.printTable(tableRows);
    input.waitForEnter();
  }

  @Override
  public List<FixedActivity> getList() {
    return controller.getList().stream().filter(a -> a instanceof FixedActivity).map(a -> (FixedActivity) a).toList();
  }
}

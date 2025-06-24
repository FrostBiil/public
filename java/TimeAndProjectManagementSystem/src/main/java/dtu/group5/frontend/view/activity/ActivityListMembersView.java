package dtu.group5.frontend.view.activity;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.controller.ActivityController;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IListView;

import java.util.ArrayList;
import java.util.List;

public class ActivityListMembersView implements IListView<BaseActivity> {
    private final ActivityController activityController;

    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();


    public ActivityListMembersView(ActivityController activityController) {
        this.activityController = activityController;
    }

    @Override
    public void runView() {
        printer.printHeader("ACTIVITY MEMBER LIST");

        // Select Activity
        String title = input.prompt(
                "Enter activity title to view",
                s -> s,
                s -> !s.isBlank(),
                "Title cannot be empty",
                false
        );

        BaseActivity activity = activityController.getList().stream()
            .filter(a -> a.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
        if (activity == null) {
            printer.printError("Activity not found");
            return;
        }


        if (activity.getAssignedCoworkers().isEmpty()) {
            printer.printError("No coworker(s) found for this activity");
            return;
        }

        List<String[]> tableRows = new ArrayList<>();
        tableRows.add(new String[]{"initial", "name"});

        for (Coworker c : activity.getAssignedCoworkers()) {
            tableRows.add(new String[]{
                    c.getInitials(),
                    c.getName()
            });
        }
        printer.printTable(tableRows);
        input.waitForEnter();
    }

    @Override
    public List<BaseActivity> getList() {
        return activityController.getList();
    }
}

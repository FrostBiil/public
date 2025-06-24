package dtu.group5.frontend.view.activity;

import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.frontend.util.MenuHandler;
import dtu.group5.frontend.util.MenuOption;
import dtu.group5.frontend.view.IListView;
import dtu.group5.frontend.view.IView;

import java.util.List;

public class ActivityListMenuView implements IView {
    private final MenuHandler menuHandler = new MenuHandler();

    private final IListView<FixedActivity> fixedActvityIListView;
    private final IListView<ProjectActivity> projectActivityIListView;

    public ActivityListMenuView(IListView<FixedActivity> fixedActvityIListView, IListView<ProjectActivity> projectActivityIListView) {
        this.fixedActvityIListView = fixedActvityIListView;
        this.projectActivityIListView = projectActivityIListView;
    }

    @Override
    public void runView() {
        List<MenuOption> options = List.of(
                new MenuOption("Back", () -> {}),                       // 0
                new MenuOption("List Fixed Activities", fixedActvityIListView::runView), // 1
                new MenuOption("List Project Activities", projectActivityIListView::runView) // 2
        );

        menuHandler.runMenu("ACTIVITY MENU", options);
    }
}
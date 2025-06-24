package dtu.group5.frontend.view.registerHours;

import dtu.group5.frontend.util.MenuHandler;
import dtu.group5.frontend.util.MenuOption;
import dtu.group5.frontend.view.IMenuView;
import dtu.group5.frontend.view.IView;

import java.util.List;

public class RegisterHoursMenuView implements IMenuView {
  final IView registerWorkedHoursView;
  final IView editWorkedHoursView;
  final IView deleteWorkedHoursView;
  final IView listTodaysRegisteredActivitiesView;
  final IView listUnregisteredActivitiesView;

  private final MenuHandler menuHandler = new MenuHandler();

  public RegisterHoursMenuView(
    IView registerWorkedHoursView,
    IView editWorkedHoursView,
    IView deleteWorkedHoursView,
    IView listTodaysRegisteredActivitiesView,
    IView listUnregisteredActivitiesView
  ) {
    this.registerWorkedHoursView = registerWorkedHoursView;
    this.editWorkedHoursView = editWorkedHoursView;
    this.deleteWorkedHoursView = deleteWorkedHoursView;
    this.listTodaysRegisteredActivitiesView = listTodaysRegisteredActivitiesView;
    this.listUnregisteredActivitiesView = listUnregisteredActivitiesView;
  }

  @Override
  public void runView() {
    List<MenuOption> options = ( List.of(
        new MenuOption("Back", () -> {}), // 0
        new MenuOption("Register Worked Hours", registerWorkedHoursView::runView),
        new MenuOption("Edit Worked Hours", editWorkedHoursView::runView),
        new MenuOption("Delete Worked Hours", deleteWorkedHoursView::runView),
        new MenuOption("List Today's Registered Activities", listTodaysRegisteredActivitiesView::runView),
        new MenuOption("List Unregistered Activities", listUnregisteredActivitiesView::runView)
      )
    );

    menuHandler.runMenu("REGISTER HOURS MENU", options);
  }
}

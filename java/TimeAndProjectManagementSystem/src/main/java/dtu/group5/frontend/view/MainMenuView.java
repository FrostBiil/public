package dtu.group5.frontend.view;

import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.util.MenuHandler;
import dtu.group5.frontend.util.MenuOption;

import java.util.List;

 // Made by Matthias (s245759)
public class MainMenuView implements IView {
  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();
  private final MenuHandler menuHandler = new MenuHandler();

  private final IView projectMenuView;
  private final IView activityMenuView;
  private final IView coworkerMenuView;
  private final IView sessionMenuView;
  private final IView registerHoursMenuView;

  //Made By Matthias (s245759)
  public MainMenuView(IView projectMenuView, IView activityMenuView, IView coworkerMenuView, IView sessionMenuView, IView registerHoursMenuView) {
    this.projectMenuView = projectMenuView;
    this.activityMenuView = activityMenuView;
    this.coworkerMenuView = coworkerMenuView;
    this.sessionMenuView = sessionMenuView;
    this.registerHoursMenuView = registerHoursMenuView;
  }

  // Made by Matthias (s245759)
  @Override
  public void runView() {
    List<MenuOption> options = List.of(
        new MenuOption("Exit", () -> System.exit(0)),
        new MenuOption("Project Menu", projectMenuView::runView),
        new MenuOption("Activity Menu", activityMenuView::runView),
        new MenuOption("Coworker Menu", coworkerMenuView::runView),
        new MenuOption("Session Menu", sessionMenuView::runView),
        new MenuOption("Register Hours Menu", registerHoursMenuView::runView)
    );

    menuHandler.runMenu("MAIN MENU", options);
  }
}

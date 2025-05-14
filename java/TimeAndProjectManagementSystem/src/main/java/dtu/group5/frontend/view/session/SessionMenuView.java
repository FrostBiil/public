package dtu.group5.frontend.view.session;

import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.util.MenuHandler;
import dtu.group5.frontend.util.MenuOption;
import dtu.group5.frontend.view.IView;

import java.util.List;

// Made by Elias (241121)
public class SessionMenuView implements IView {
  private final Session session = Session.getInstance();

  private final IView loginView;
  private final IView logoutView;
  private final IView sessionInformationView;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();
  private final MenuHandler menuHandler = new MenuHandler();

  // Made by Elias (241121)
  public SessionMenuView(IView loginView, IView logoutView, IView sessionInformationView) {
    this.loginView = loginView;
    this.logoutView = logoutView;
    this.sessionInformationView = sessionInformationView;
  }

  // Made by Elias (241121)
  @Override
  public void runView() {
    List<MenuOption> options = ( List.of(
        new MenuOption("Back", () -> {}), // 0
        new MenuOption("login", loginView::runView),
        new MenuOption("logout", logoutView::runView),
        new MenuOption("Session Information", sessionInformationView::runView)
      )
    );

    menuHandler.runMenu("SESSION MENU", options);
  }
}

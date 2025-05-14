package dtu.group5.frontend.view;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.util.MenuOption;
import dtu.group5.frontend.util.RedirectMenuHandler;

import java.util.List;
// Made by Elias (241121)
public class LoginMenuView implements IView {
    private final ConsolePrinter printer = ConsolePrinter.getInstance();
    private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();
    private final RedirectMenuHandler redirectMenuHandler;

    private final IView mainView;
    private final IView loginView;
    private final ICreateView<Coworker> createView;

    // Made by Elias (241121)
    public LoginMenuView(IView mainView, IView loginView, ICreateView<Coworker> createView) {
        this.mainView = mainView;
        this.loginView = loginView;
        this.createView = createView;

        redirectMenuHandler = new RedirectMenuHandler(mainView, () -> Session.getInstance().isLoggedIn());
    }


    // Made by Elias (241121)
    @Override
    public void runView() {
        List<MenuOption> options = List.of(
            new MenuOption("Exit", () -> System.exit(0)),
            new MenuOption("Login", loginView::runView),
            new MenuOption("Register Coworker", createView::runView)
        );

        redirectMenuHandler.runMenu("MAIN LOGIN MENU", options);
    }
}


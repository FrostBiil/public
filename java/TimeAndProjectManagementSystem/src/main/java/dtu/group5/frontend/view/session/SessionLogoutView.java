package dtu.group5.frontend.view.session;

import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IView;

public class SessionLogoutView implements IView {
  Session session = Session.getInstance();

  ConsolePrinter printer = ConsolePrinter.getInstance();

  @Override
  public void runView() {
    printer.printHeader("LOGOUT");

    if (session.getLoggedInUser() == null) {
      printer.printError("No user is currently logged in.");
      return;
    }

    session.logout();

    printer.printSuccess("Logout successful");
  }
}

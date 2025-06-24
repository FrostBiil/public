package dtu.group5.frontend.view.session;

import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IView;

public class SessionInformationView implements IView {
    Session session = Session.getInstance();

    ConsolePrinter printer = ConsolePrinter.getInstance();
    ConsoleInputHelper input = ConsoleInputHelper.getInstance();

    @Override
    public void runView() {
        printer.printHeader("SESSION INFORMATION");

        if (session.getLoggedInUser() == null) {
            printer.printError("No user is currently logged in.");
            return;
        }

        printer.printSuccess("Logged in as: " + session.getLoggedInUser().getName() + " (Initials: " + session.getLoggedInUser().getInitials() + ")" );
        input.waitForEnter();
    }
}

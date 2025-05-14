package dtu.group5.frontend.view.session;

import dtu.group5.frontend.session.Session;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IView;

import java.util.Optional;

import static dtu.group5.frontend.session.Session.login;

// Made by Matthias (s245759)
public class SessionLoginView implements IView {
  Session session = Session.getInstance();

  ConsolePrinter printer = ConsolePrinter.getInstance();
  ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  // Made by Matthias (s245759)
  @Override
  public void runView() {
    printer.printHeader("LOGIN");

    String initials = input.prompt(
      "Please enter initials",
      s -> s,
      s -> s.length() <= 4,
      "Initials is at most 4 long",
      false
    );

    Optional<String> login = login(initials);

    if (login.isPresent()) {
      printer.printError(login.get());
      return;
    }

    printer.printSuccess("Successfully logged in as " + initials);
  }
}

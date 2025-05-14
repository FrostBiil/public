package dtu.group5.frontend.view.coworker;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.ICreateView;

import java.util.Optional;

// Made by Matthias(s245759)
public class CoworkerCreateView implements ICreateView<Coworker> {
  private final Controller<Coworker> controller;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public CoworkerCreateView(Controller<Coworker> controller) {
    this.controller = controller;
  }

  // Made By Matthias(s245759)
  @Override
  public void runView() {
    create();
  }

  // Made By Matthias(s245759)
  @Override
  public Coworker create() {
    printer.printHeader("CREATE COWORKER");

    String initials = input.prompt(
      "Enter initials",
        s -> s,
        s -> s.length() >= 2 && s.length() <= 4,
        "Initials must be 2–4 characters",
      false
    );

    String name = input.prompt(
      "Enter name",
        s -> s,
        s -> !s.isBlank(),
        "Name cannot be empty",
      false
    );

    Coworker coworker = new Coworker(initials, name);

    // Create coworker
    Optional<String> responce = controller.handleCreate(coworker);

    // Display responce
    if (responce.isPresent()) {
      printer.printError(responce.get());
      return null;
    }
    printer.printSuccess("Coworker created successfully!");
    return coworker;
  }
}

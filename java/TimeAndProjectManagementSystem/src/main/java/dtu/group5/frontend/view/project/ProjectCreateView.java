package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Project;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.ICreateView;

import java.util.Optional;

// Made By Elias(241121)
public class ProjectCreateView implements ICreateView<Project> {
  private final Controller<Project> controller;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  // Made By Matthias (s245759)
  public ProjectCreateView(Controller<Project> controller) {
    this.controller = controller;
  }

  // Made By Matthias (s245759)
  @Override
  public void runView() {
    create();
  }

  // Made By Matthias (s245759)
  @Override
  public Project create() {
    printer.printHeader("CREATE PROJECT");

    String title = input.prompt(
      "Enter project title",
      s -> s,
      s -> !s.isBlank(),
      "Title cannot be empty",
      false
    );

    Project project = new Project(-1, title);

    // Create project
    Optional<String> responce = controller.handleCreate(project);

    // Display responce
    if (responce.isPresent()) {
      printer.printError(responce.get());
      return null;
    }
    printer.printSuccess("Project created successfully!");
    return project;
  }
}

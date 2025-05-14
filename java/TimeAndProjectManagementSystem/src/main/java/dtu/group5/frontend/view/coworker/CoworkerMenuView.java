package dtu.group5.frontend.view.coworker;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.util.MenuHandler;
import dtu.group5.frontend.util.MenuOption;
import dtu.group5.frontend.view.ICreateView;
import dtu.group5.frontend.view.IDeleteView;
import dtu.group5.frontend.view.IListView;
import dtu.group5.frontend.view.IView;

import java.util.List;

//Made By Matthias(s245759)
public class CoworkerMenuView implements IView {
  private final Controller<Coworker> controller;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();
  private final MenuHandler menuHandler = new MenuHandler();

  private final IView createView;
  private final IView listView;
    private final IView editView;
    private final IDeleteView deleteView;

  //Made By Matthias(s245759)
  public CoworkerMenuView(Controller<Coworker> controller, ICreateView<Coworker> createView, IListView<Coworker> listView, IView editView, IDeleteView deleteView) {
    this.controller = controller;
    this.createView = createView;
    this.listView = listView;
    this.editView = editView;
    this.deleteView = deleteView;
  }

  //Made By Matthias(s245759)
  @Override
  public void runView() {
    List<MenuOption> options = List.of(
        new MenuOption("Back", () -> {}), // 0
        new MenuOption("Create Coworkers", createView::runView), // 1
        new MenuOption("Delete Coworker", deleteView::runView), // 2
        new MenuOption("List Coworkers", listView::runView),   // 3
        new MenuOption("Edit Coworker", editView::runView) // 4
    );

    menuHandler.runMenu("COWORKER MENU", options);
  }
}
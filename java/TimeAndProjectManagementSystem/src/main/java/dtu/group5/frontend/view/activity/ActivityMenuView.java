package dtu.group5.frontend.view.activity;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.util.MenuHandler;
import dtu.group5.frontend.util.MenuOption;
import dtu.group5.frontend.view.ICreateView;
import dtu.group5.frontend.view.IDeleteView;
import dtu.group5.frontend.view.IEditView;
import dtu.group5.frontend.view.IListView;
import dtu.group5.frontend.view.IView;

import java.util.List;

public class ActivityMenuView implements IView {
  private final Controller<BaseActivity> controller;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();
  private final MenuHandler menuHandler = new MenuHandler();

  private final ICreateView<BaseActivity> createView;
  private final IView  listMenuView;
  private final IEditView<BaseActivity> editView;
  private final IEditView<BaseActivity> assignMemberView;
  private final IListView<BaseActivity> activityMemberListView;
  private final IDeleteView deleteView;

  public ActivityMenuView(Controller<BaseActivity> controller, ICreateView<BaseActivity> createView, IView listMenuView, IEditView<BaseActivity> editView, IEditView<BaseActivity> assignMemberView, IListView<BaseActivity> activityMemberListView, IDeleteView deleteView) {
    this.controller = controller;
    this.createView = createView;
    this.listMenuView = listMenuView;
    this.editView = editView;
    this.assignMemberView = assignMemberView;
    this.activityMemberListView = activityMemberListView;
    this.deleteView = deleteView;
  }

  @Override
  public void runView() {
    List<MenuOption> options = List.of(
      new MenuOption("Back", () -> {}),                       // 0
      new MenuOption("Create Activity", createView::runView), // 1
      new MenuOption("Delete Activity", deleteView::runView), // 2
      new MenuOption("List Activities Menu", listMenuView::runView),   // 3
      new MenuOption("Edit Activity", editView::runView),      // 4
      new MenuOption("Assign Member to Activity", assignMemberView::runView), // 5
      new MenuOption("List Activity Members", activityMemberListView::runView) // 6
    );

    menuHandler.runMenu("ACTIVITY MENU", options);
  }
}
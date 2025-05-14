package dtu.group5.frontend.view.project;

import dtu.group5.backend.model.Project;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.util.MenuHandler;
import dtu.group5.frontend.util.MenuOption;
import dtu.group5.frontend.view.ICreateView;
import dtu.group5.frontend.view.IDeleteView;
import dtu.group5.frontend.view.IEditView;
import dtu.group5.frontend.view.IListView;
import dtu.group5.frontend.view.IReportView;
import dtu.group5.frontend.view.IView;

import java.util.List;

// Made By Elias(241121)
public class ProjectMenuView implements IView {
  private final Controller<Project> controller;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();
  private final MenuHandler menuHandler = new MenuHandler();

  private final ICreateView<Project> createView;
  private final IListView<Project> listView;
  private final IEditView<Project> editView;
  private final IEditView<Project> addProjectMemberView;
  private final IEditView<Project> removeProjectMemberView;
  private final IListView<Project> projectMemberListView;
  private final IListView<Project> projectActivityListView;
  private final IReportView projectReportView;
  private final IDeleteView deleteView;
  private final IListView<Project> projectActivityAvailableListView;

  // Made By Elias(241121)
  public ProjectMenuView(Controller<Project> controller, ICreateView<Project> createView, IListView<Project> listView, IEditView<Project> editView, IEditView<Project> addProjectMemberView, IEditView<Project> removeProjectMemberView, IListView<Project> projectMemberListView, IListView<Project> projectActivityListView, IReportView projectReportView, IDeleteView deleteView, IListView<Project> projectActivityAvailableListView) {
    this.controller = controller;
    this.createView = createView;
    this.listView = listView;
    this.editView = editView;
    this.addProjectMemberView = addProjectMemberView;
    this.removeProjectMemberView = removeProjectMemberView;
    this.projectMemberListView = projectMemberListView;
    this.projectActivityListView = projectActivityListView;
    this.projectReportView = projectReportView;
    this.deleteView = deleteView;
    this.projectActivityAvailableListView = projectActivityAvailableListView;
  }
  // Made By Elias(241121)
  @Override
  public void runView() {
    List<MenuOption> options = List.of(
        new MenuOption("Back", () -> {}),
        new MenuOption("Create Project", createView::runView),
        new MenuOption("Delete Project", deleteView::runView),
        new MenuOption("List Projects", listView::runView),
        new MenuOption("Edit Project", editView::runView),
        new MenuOption("Add Project Member", addProjectMemberView::runView),
        new MenuOption("Remove Project Member", removeProjectMemberView::runView),
        new MenuOption("List Project Members", projectMemberListView::runView),
        new MenuOption("List Project Activities", projectActivityListView::runView),
        new MenuOption("List Available Coworkers for Activity", projectActivityAvailableListView::runView),
        new MenuOption("Project Work Report", projectReportView::runView)
    );

    menuHandler.runMenu("PROJECT MENU", options);
  }
}

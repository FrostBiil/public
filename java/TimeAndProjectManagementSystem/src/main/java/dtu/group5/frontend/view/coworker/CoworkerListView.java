package dtu.group5.frontend.view.coworker;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.controller.Controller;
import dtu.group5.frontend.util.ConsoleInputHelper;
import dtu.group5.frontend.util.ConsolePrinter;
import dtu.group5.frontend.view.IListView;

import java.util.ArrayList;
import java.util.List;

// Made By Elias(s241121)
public class CoworkerListView implements IListView<Coworker> {
  private final Controller<Coworker> controller;

  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final ConsoleInputHelper input = ConsoleInputHelper.getInstance();

  public CoworkerListView(Controller<Coworker> controller) {
    this.controller = controller;
  }

  // Made By  Elias(s241121)
  @Override
  public void runView() {
    printer.printHeader("COWORKER LIST");

    List<Coworker> coworkerList = this.getList();

    if (coworkerList.isEmpty()) {
      printer.printInfo("No coworkers available.");
      return;
    }

    List<String[]> tableRows = new ArrayList<>();
    tableRows.add(new String[]{"Initials", "Name"});

    for (Coworker c : coworkerList) {
        tableRows.add(new String[]{
            c.getInitials(),
            c.getName()
        });
    }

    printer.printTable(tableRows);
    input.waitForEnter();
  }

  // Made By Matthias(s245759)
  @Override
  public List<Coworker> getList() {
    return controller.getList();
  }
}

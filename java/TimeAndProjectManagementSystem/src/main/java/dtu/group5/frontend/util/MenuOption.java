package dtu.group5.frontend.util;

public class MenuOption {
  private final String label;
  private final Runnable action;

  public MenuOption(String label, Runnable action) {
    this.label = label;
    this.action = action;
  }

  public String getLabel() {
    return label;
  }

  public void execute() {
    action.run();
  }
}
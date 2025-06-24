package dtu.group5.frontend.util;

public class ConsoleFormatter {

  public int getTerminalWidth() {
    // Fallback til 80, da Java ikke kan læse terminalbredde direkte
    return 80;
  }

  public String repeat(char c, int count) {
    return String.valueOf(c).repeat(Math.max(0, count));
  }

  public String formatColumns(String... columns) {
    int width = getTerminalWidth();
    int colWidth = width / columns.length;

    StringBuilder sb = new StringBuilder();
    for (String col : columns) {
      sb.append(String.format("%-" + colWidth + "s", col));
    }
    return sb.toString();
  }
}

package dtu.group5.frontend.util;

// Made by Matthias (s245759)
public class ConsoleFormatter {

  // Made by Matthias (s245759)
  public int getTerminalWidth() {
    // Fallback til 80, da Java ikke kan læse terminalbredde direkte
    return 80;
  }

  // Made by Matthias (s245759)
  public String repeat(char c, int count) {
    return String.valueOf(c).repeat(Math.max(0, count));
  }

  // Made by Matthias (s245759)
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

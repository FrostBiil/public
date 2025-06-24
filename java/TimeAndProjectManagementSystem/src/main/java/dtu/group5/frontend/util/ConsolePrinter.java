package dtu.group5.frontend.util;

import java.util.List;

public class ConsolePrinter {
  private static final ConsolePrinter instance = new ConsolePrinter();
  private final ConsoleFormatter formatter = new ConsoleFormatter();

  private ConsolePrinter() {;
    // Private constructor to prevent instantiation
  }

  public static ConsolePrinter getInstance() {
    return instance;
  }

  public void printHeader(String title) {
    String line = formatter.repeat('=', title.length() + 8);
    System.out.println();
    System.out.println(ConsoleColors.BLUE + line);
    System.out.println("  " + title.toUpperCase());
    System.out.println(line + ConsoleColors.RESET);
  }

  public void printSubHeader(String label) {
    System.out.println();
    System.out.println(ConsoleColors.CYAN + "-- " + label + ConsoleColors.RESET);
  }

  public void printLine() {
    System.out.println(formatter.repeat('-', 30));
  }

  public void printBlankLine() {
    System.out.println();
  }

  public void printError(String message) {
    System.out.println(ConsoleColors.RED + "[ERROR] " + message + ConsoleColors.RESET);
  }

  public void printInfo(String message) {
    System.out.println(ConsoleColors.YELLOW + "[INFO] " + message + ConsoleColors.RESET);
  }

  public void printSuccess(String message) {
    System.out.println(ConsoleColors.GREEN + "[SUCCESS] " + message + ConsoleColors.RESET);
  }

  public void printOption(int number, String description) {
    System.out.printf("[%d] %s%n", number, description);
  }

  public void printKeyValue(String key, String value) {
    System.out.printf("%-15s: %s%n", key, value);
  }

  public void printTable(List<String[]> rows) {

    // Dont print empty table
    if (rows.isEmpty()) return;

    // Assume each colum has the same amount of items and get the amount
    int columns = rows.getFirst().length;
    int[] columnWidths = new int[columns];

    // Find the maximum width of each column
    for (String[] row : rows) {
      for (int i = 0; i < columns; i++) {
        if (row[i].length() > columnWidths[i]) {
          columnWidths[i] = row[i].length();
        }
      }
    }

    // Calculate total width of the table with spaces and "|"
    int totalWidth = columns + 1;
    for (int width : columnWidths) {
      totalWidth += width + 2; // 1 space before + content + 1 space after
    }

    // Build the separator line
    String separator = formatter.repeat('-', totalWidth);

    // Print top border
    System.out.println(separator);

    // Print all rows
    for (String[] row : rows) {
      StringBuilder rowBuilder = new StringBuilder();
      rowBuilder.append("|");
      for (int i = 0; i < columns; i++) {
        rowBuilder.append(" ");
        rowBuilder.append(String.format("%-" + columnWidths[i] + "s", row[i]));
        rowBuilder.append(" |");
      }
      System.out.println(rowBuilder);

      // If header row, print separator after it
      if (row == rows.getFirst()) {
        System.out.println(separator);
      }
    }

    // Print bottom border
    System.out.println(separator);
  }

  public void printList(List<String> list) {
    for (String item : list) {
      System.out.println("- " + item);
    }
  }
}

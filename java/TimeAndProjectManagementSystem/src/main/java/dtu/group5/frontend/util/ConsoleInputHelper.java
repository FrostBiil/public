package dtu.group5.frontend.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

// Made by Matthias (s245759)
public class ConsoleInputHelper {
  public static final ConsoleInputHelper instance = new ConsoleInputHelper();

  // Made by Matthias (s245759)
  private ConsoleInputHelper() {;
    // Private constructor to prevent instantiation
  }

  // Made by Matthias (s245759)
  public static ConsoleInputHelper getInstance() {
    return instance;
  }

  private final Scanner scanner = new Scanner(System.in);
  private final ConsolePrinter printer = ConsolePrinter.getInstance();
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

  // Made by Matthias (s245759)
  public <T> T prompt(String prompt, Parser<T> parser, Validator<T> validator, String errorMsg, boolean optional) {
    return prompt(prompt, parser, validator, errorMsg, optional, null);
  }

  // Made by Elias (241121)
  public <T> T prompt(String prompt, Parser<T> parser, Validator<T> validator, String errorMsg, boolean optional, T errorResponse) {
    while (true) {
      System.out.print(prompt + (optional ? " (optional): " : ": "));
      String input = scanner.nextLine().trim();

      if (optional && input.isEmpty()) return errorResponse;

      try {
        T value = parser.parse(input);
        if (validator == null || validator.validate(value)) return value;
      } catch (Exception e) {
        printer.printError(e.getMessage());
        continue;
      }

      printer.printError(errorMsg);
    }
  }

  // Made by Elias (241121)
  public<T> T promtList(String prompt, List<T> options, boolean optional) {
    if (options.isEmpty()) {
      printer.printError("No options available.");
      return null;
    }

    // Print muligheder
    System.out.println(prompt + ":");
    for (int i = 0; i < options.size(); i++) {
      System.out.printf("[%d] %s%n", i + 1, options.get(i));
    }

    return prompt(
      "Select",
      input -> {
        int index = Integer.parseInt(input.trim());
        return options.get(index - 1);
      },
      selected -> true,
      "Invalid choice. Please enter a number from the list.",
      optional
    );
  }

  // Made by Matthias (s245759)
  public <T extends Enum<T>> T promptEnum(String prompt, Class<T> enumClass, boolean optional) {
    T[] values = enumClass.getEnumConstants();

    // Formatter enum options som tekst
    StringBuilder optionsText = new StringBuilder(prompt + ":\n");
    for (int i = 0; i < values.length; i++) {
      optionsText.append(String.format("[%d] %s%n", i + 1, values[i]));
    }

    System.out.print(optionsText);

    // Brug den generiske prompt
    return prompt(
      "Select",
      input -> {
        int index = Integer.parseInt(input.trim());
        return values[index - 1];
      },
      selected -> true,
      "Invalid choice. Please enter a valid number.",
      optional
    );
  }

  // Made by Matthias (s245759)
  public <T> T promptSelection(String prompt, List<T> options, Formatter<T> formatter, boolean optional) {
    if (options.isEmpty()) {
      printer.printError("No options available.");
      return null;
    }

    // Print muligheder
    System.out.println(prompt + ":");
    for (int i = 0; i < options.size(); i++) {
      System.out.printf("[%d] %s%n", i + 1, formatter.format(options.get(i)));
    }

    return prompt(
      "Select",
      input -> {
        int index = Integer.parseInt(input.trim());
        return options.get(index - 1);
      },
      selected -> true,
      "Invalid choice. Please enter a number from the list.",
      optional
    );
  }

  // Made by Elias (241121)
  public void waitForEnter() {
    System.out.print(ConsoleColors.CYAN+"Press Enter to continue...");
    scanner.nextLine();
  }

  // Made by Matthias (s245759)
  public interface Formatter<T> {
    String format(T obj);
  }

  // Made by Matthias (s245759)
  @FunctionalInterface
  public interface Parser<T> {
    T parse(String input) throws Exception;
  }

  // Made by Matthias (s245759)
  @FunctionalInterface
  public interface Validator<T> {
    boolean validate(T input);
  }
}

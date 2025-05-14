package dtu.group5.backend.util;

import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectType;

import java.util.Date;

import static dtu.group5.backend.util.DateUtil.parseDate;

// Made by Mattias (s245759)
public class FieldParser {
  // Made by Mattias (s245759)
  public static Object parseField(String field, Object value) {
    return switch (field.toLowerCase()) {
      case "startdate", "enddate" -> (value instanceof Date) ? value : parseDate(value.toString());
      case "finished" -> (value instanceof Boolean) ? value : FieldParser.parseBoolean(value.toString());
      case "startyear", "endyear", "startweek", "endweek" -> (value instanceof Integer) ? value : Integer.parseInt(value.toString());
      case "type" -> ProjectType.fromString(value.toString());
      case "status" -> Project.ProjectStatus.fromString(value.toString());
      case "expectedhours" -> (value instanceof Double) ? value : Double.parseDouble(value.toString());
      default -> value.toString();
    };
  }

  // Made by Elias (s241121)
  public static Boolean parseBoolean(String value) {
    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("y") || value.equalsIgnoreCase("ja"))
      return true;

    else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("no") || value.equalsIgnoreCase("n") || value.equalsIgnoreCase("nej"))
      return false;

    else
      throw new IllegalArgumentException("Invalid boolean value: " + value);
  }
}

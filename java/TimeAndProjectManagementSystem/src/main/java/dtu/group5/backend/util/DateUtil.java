package dtu.group5.backend.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
  public static Date parseDate(String dateString) throws IllegalArgumentException {
    try {
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
        if (date == null) {
          throw new IllegalArgumentException("Invalid date format. Use dd-MM-yyyy");
        }
        return date;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid date format. Use dd-MM-yyyy");
    }
  }

  public static String formatDate(Date date) {
    return new SimpleDateFormat("dd-MM-yyyy").format(date);
  }

  public static Date stripTime(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static Date getCurrentDate() {
    Date date = new Date();
    return stripTime(date);
  }

  // Made by Elias (s241121)
  /**
   * Convert a year in the format YY and week to a date starting monday
   * @param year in format YY
   * @param weekNumber week num
   * @return a Date object
   */
  public static Date convertToDate(int year, int weekNumber) {
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int century = (currentYear / 100) * 100; // Get the current century
    calendar.clear();
    calendar.set(Calendar.YEAR, century + year);
    calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return calendar.getTime();
  }

}

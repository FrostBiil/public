package dtu.group5.backend.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static dtu.group5.backend.util.DateUtil.stripTime;

public class ProjectActivity extends BaseActivity {
  private final int projectNumber;

  private double expectedHours;
  private int startYear;
  private int startWeekNumber;
  private int endYear;
  private int endWeekNumber;

  private final Map<Coworker, Map<Date, Double>> workedHours = new HashMap<>();

  public ProjectActivity(int projectNumber, String title, double expectedHours, boolean finished,
                         String description, Set<Coworker> coworkersToAssign,
                         int startYear, int startWeekNumber, int endYear, int endWeekNumber,
                         Map<Coworker, Map<Date, Double>> workedHoursPerCoworker) {
    super(title, finished, description, coworkersToAssign);
    this.projectNumber = projectNumber;
    this.expectedHours = expectedHours;
    this.startYear = startYear;
    this.startWeekNumber = startWeekNumber;
    this.endYear = endYear;
    this.endWeekNumber = endWeekNumber;

    for (Map.Entry<Coworker, Map<Date, Double>> entry : workedHoursPerCoworker.entrySet()) {
      Coworker coworker = entry.getKey();
      Map<Date, Double> dailyMap = entry.getValue();
      this.workedHours.put(coworker, dailyMap);
    }
  }


  public int getProjectNumber() {
    return projectNumber;
  }

  public int getStartYear() {
    return startYear;
  }

  public int getStartWeekNumber() {
    return startWeekNumber;
  }

  @Override
  public Integer getStartDate() {
    return startWeekNumber;
  }

  @Override
  public Integer getEndDate() {
    return endWeekNumber;
  }

  public int getEndYear() {
    return endYear;
  }

  public int getEndWeekNumber() {
    return endWeekNumber;
  }

  public double getExpectedHours() {
    return expectedHours;
  }

  public Map<Coworker, Map<Date, Double>> getWorkedHoursPerCoworker() {
    return workedHours;
  }

  public void setExpectedHours(double expectedHours) {
    this.expectedHours = expectedHours;
  }

  public void setStartYear(int startYear) {
    this.startYear = startYear;
  }

  public void setStartWeekNumber(int startWeekNumber) {
    this.startWeekNumber = startWeekNumber;
  }

  public void setEndYear(int endYear) {
    this.endYear = endYear;
  }

  public void setEndWeekNumber(int endWeekNumber) {
    this.endWeekNumber = endWeekNumber;
  }

  public void setWorkedHours(Coworker coworker, Date date, double hours) {
    Map<Date, Double> daily = workedHours.computeIfAbsent(coworker, k -> new HashMap<>());
    daily.put(stripTime(date), hours);
  }
}

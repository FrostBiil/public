package dtu.group5.backend.service.activity.edit_activity.edit_activity_project;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.activity.edit_activity.ActivityFieldEditor;

import java.util.Optional;

// Made by Elias (241121)
public class StartYearEditor implements ActivityFieldEditor {
  @Override
  public boolean supports(String fieldName) {
    return "startyear".equalsIgnoreCase(fieldName);
  }

  @Override
  public Optional<String> edit(BaseActivity activity, Object value) {
    if (!(activity instanceof ProjectActivity projectActivity))
      return Optional.of("Activity is not a project activity");

    if (!(value instanceof Integer newStartYear))
      return Optional.of("Invalid value type for start year. Expected an Integer");

    if (newStartYear < 0 || newStartYear >= 99)
      return Optional.of("Start year must be between 0 and 99");

    int currentStartWeek = projectActivity.getStartWeekNumber();
    int endYear = projectActivity.getEndYear();
    int endWeek = projectActivity.getEndWeekNumber();

    if (newStartYear > endYear || (newStartYear == endYear && currentStartWeek > endWeek))
      return Optional.of("Start time must be equal to or before end time");

    projectActivity.setStartYear(newStartYear);
    return Optional.empty();
  }
}



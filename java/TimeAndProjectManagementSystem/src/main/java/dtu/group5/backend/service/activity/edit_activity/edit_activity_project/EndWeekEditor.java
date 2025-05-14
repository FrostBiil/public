package dtu.group5.backend.service.activity.edit_activity.edit_activity_project;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.activity.edit_activity.ActivityFieldEditor;

import java.util.Optional;

// Made by Elias (241121)
public class EndWeekEditor implements ActivityFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "endweek".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(BaseActivity activity, Object value) {
        if (!(activity instanceof ProjectActivity projectActivity))
            return Optional.of("Activity is not a project activity");

        if (value instanceof Integer newEndWeek) {
            if(newEndWeek < 0 || newEndWeek >= 52) {
                return Optional.of("End week must be between 1 and 52");
            }
            if (projectActivity.getStartYear() > projectActivity.getEndYear() || (projectActivity.getStartYear() == projectActivity.getEndYear() && projectActivity.getStartWeekNumber() > newEndWeek)) {
                return Optional.of("End time must be equal to or before end time");
            }


            projectActivity.setEndWeekNumber(newEndWeek);
        } else {
            return Optional.of("Invalid value type for end week. Expected an Integer");
        }
        return Optional.empty();
    }
}

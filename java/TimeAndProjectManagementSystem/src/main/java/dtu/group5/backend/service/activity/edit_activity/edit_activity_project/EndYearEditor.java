package dtu.group5.backend.service.activity.edit_activity.edit_activity_project;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.activity.edit_activity.ActivityFieldEditor;

import java.util.Optional;

public class EndYearEditor implements ActivityFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "endyear".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(BaseActivity activity, Object value) {
        if (!(activity instanceof ProjectActivity projectActivity))
            return Optional.of("Activity is not a project activity");

        if (value instanceof Integer newEndYear) {
            if(newEndYear < 0 || newEndYear >= 99) {
                return Optional.of("End year must be between 0 and 99");
            }
            if (projectActivity.getStartYear() > newEndYear || (projectActivity.getStartYear() == newEndYear && projectActivity.getStartWeekNumber() > projectActivity.getEndWeekNumber())) {
                return Optional.of("End time must be equal to or after start time");
            }


            projectActivity.setEndYear(newEndYear);
        } else {
            return Optional.of("Invalid value type for end year. Expected an Integer");
        }
        return Optional.empty();
    }
}

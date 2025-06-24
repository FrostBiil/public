package dtu.group5.backend.service.activity.edit_activity.edit_activity_project;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.activity.edit_activity.ActivityFieldEditor;

import java.util.Optional;

public class ExpectedHoursEditor implements ActivityFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "expectedhours".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(BaseActivity activity, Object value) {
        if (!(activity instanceof ProjectActivity projectActivity))
            return Optional.of("Activity is not a project activity");

        if (value instanceof Double newExpectedHours) {
            if (newExpectedHours < 0) {
                return Optional.of("Expected hours must be greater than or equal to 0");
            }

            projectActivity.setExpectedHours(newExpectedHours);
        } else {
            return Optional.of("Invalid value type for expected hours. Expected an Double");
        }
        return Optional.empty();
    }
}


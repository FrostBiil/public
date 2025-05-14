package dtu.group5.backend.service.activity.edit_activity.edit_activity_fixed;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.service.activity.edit_activity.ActivityFieldEditor;

import java.util.Date;
import java.util.Optional;

// Made by Mattias (s245759)
public class StartDateEditor implements ActivityFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "startdate".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(BaseActivity activity, Object value) {
        if (!(activity instanceof FixedActivity fixedActvity))
            return Optional.of("Activity is not a fixed activity");

        if (value instanceof Date newStartDate) {
            if (newStartDate.after(fixedActvity.getEndDate())) {
                return Optional.of("start date can't be after end date");
            }
            fixedActvity.setStartDate(newStartDate);
        } else {
           return Optional.of("Invalid value type for start date. Expected a Date");
        }
        return Optional.empty();
    }
}

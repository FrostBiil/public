package dtu.group5.backend.service.activity.edit_activity;

import dtu.group5.backend.model.BaseActivity;

import java.util.Optional;

// Made by Elias (241121)
public class FinishedEditor implements ActivityFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "finished".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(BaseActivity activity, Object value) {
        if (value instanceof Boolean newFinished) {
            activity.setFinished(newFinished);
        } else {
            return Optional.of("Invalid value type for finished. Expected a boolean");
        }
        return Optional.empty();
    }
}

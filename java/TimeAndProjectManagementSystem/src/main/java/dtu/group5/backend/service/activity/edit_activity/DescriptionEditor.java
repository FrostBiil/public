package dtu.group5.backend.service.activity.edit_activity;

import dtu.group5.backend.model.BaseActivity;

import java.util.Optional;

public class DescriptionEditor implements ActivityFieldEditor {
  @Override
  public boolean supports(String fieldName) {
    return "description".equals(fieldName);
  }

  @Override
  public Optional<String> edit(BaseActivity activity, Object value) {
    if (value instanceof String newDescription) {
      if (newDescription.isEmpty()) {
        return Optional.of("Description cannot be null or empty");
      }
      activity.setDescription(newDescription);
    } else {
      return Optional.of("Invalid value type for description. Expected a string");
    }
    return Optional.empty();
  }
}

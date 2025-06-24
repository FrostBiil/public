package dtu.group5.backend.service.activity.edit_activity;

import dtu.group5.backend.model.BaseActivity;

import java.util.Optional;

public interface ActivityFieldEditor {
  boolean supports(String fieldName);
  Optional<String> edit(BaseActivity activity, Object value);
}

package dtu.group5.backend.service.activity.edit_worked_hours;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.ProjectActivity;

import java.util.Date;
import java.util.Optional;

// Made by Mattias (s245759)
public interface WorkedHoursEditor {
    boolean supports(String fieldName);
    Optional<String> edit(ProjectActivity activity, Coworker coworker, Date date, double value);
}

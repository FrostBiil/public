package dtu.group5.backend.service.project.edit_project;

import dtu.group5.backend.model.Project;

import java.util.Date;
import java.util.Optional;

// Made by Elias (s241121)
public class StartDateEditor  implements ProjectFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "startdate".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(Project project, Object value) {
        if (value instanceof Date newStartDate) {
            project.setStartDate(newStartDate);
        } else {
            return Optional.of("Invalid value type for start date. Expected a Date");
        }
        return Optional.empty();
    }
}

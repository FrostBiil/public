package dtu.group5.backend.service.project.edit_project;

import dtu.group5.backend.model.Project;

import java.util.Date;
import java.util.Optional;

public class EndDateEditor implements ProjectFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "enddate".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(Project project, Object value) {
        if (value instanceof Date newEndDate) {
            if (project.getStartDate() != null && newEndDate.before(project.getStartDate())) {
                return Optional.of("End date cannot be before start date");
            }
            project.setEndDate(newEndDate);
        } else {
            return Optional.of("Invalid value type for end date. Expected a Date");
        }
        return Optional.empty();
    }
}

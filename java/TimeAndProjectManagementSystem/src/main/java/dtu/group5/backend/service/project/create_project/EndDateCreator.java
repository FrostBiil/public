package dtu.group5.backend.service.project.create_project;

import dtu.group5.backend.model.Project;

import java.util.Date;
import java.util.Optional;

public class EndDateCreator implements ProjectFieldCreator {
    @Override
    public boolean supports(String fieldName) {
        return "enddate".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> create(Project project, Object value) {
        if (value instanceof Date endDate) {
            if (project.getStartDate() != null && endDate.before(project.getStartDate())) {
                return Optional.of("End date cannot be before start date");
            }
            project.setEndDate(endDate);
            return Optional.empty();
        }
        return Optional.of("Invalid value type for end date. Expected a Date");
    }
}

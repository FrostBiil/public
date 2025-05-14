package dtu.group5.backend.service.project.create_project;

import dtu.group5.backend.model.Project;

import java.util.Date;
import java.util.Optional;

// Made by Mattias (s245759)
public class StartDateCreator implements ProjectFieldCreator {
    @Override
    public boolean supports(String fieldName) {
        return "startdate".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> create(Project project, Object value) {
        if (value instanceof Date startDate) {
            project.setStartDate(startDate);
            return Optional.empty();
        }
        return Optional.of("Invalid value type for start date. Expected a Date");
    }
}

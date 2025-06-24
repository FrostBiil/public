package dtu.group5.backend.service.project.create_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

public class DescriptionCreator implements ProjectFieldCreator {
    @Override
    public boolean supports(String fieldName) {
        return "description".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> create(Project project, Object value) {
        if (value instanceof String description) {
            project.setDescription(description);
            return Optional.empty();
        }
        return Optional.of("Invalid description format");
    }
}

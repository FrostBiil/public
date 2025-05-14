package dtu.group5.backend.service.project.create_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

// Made by Mattias (s245759)
public class TitleCreator implements ProjectFieldCreator {
    @Override
    public boolean supports(String fieldName) {
        return "title".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> create(Project project, Object value) {
        if (value instanceof String title && !title.isBlank()) {
            project.setTitle(title);
            return Optional.empty();
        }
        return Optional.of("Project title is required");
    }
}

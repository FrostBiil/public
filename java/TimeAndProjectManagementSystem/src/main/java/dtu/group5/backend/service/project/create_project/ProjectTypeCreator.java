package dtu.group5.backend.service.project.create_project;

import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectType;

import java.util.Optional;

// Made by Mattias (s245759)
public class ProjectTypeCreator implements ProjectFieldCreator {
    @Override
    public boolean supports(String fieldName) {
        return "type".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> create(Project project, Object value) {
        if (value instanceof ProjectType type) {
            project.setType(type);
            return Optional.empty();
        }
        return Optional.of("Invalid project type");
    }
}

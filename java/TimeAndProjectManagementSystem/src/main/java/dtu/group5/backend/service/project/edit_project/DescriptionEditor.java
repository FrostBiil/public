package dtu.group5.backend.service.project.edit_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

// Made by Elias (s241121)
public class DescriptionEditor implements ProjectFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "description".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(Project project, Object value) {
        if (value instanceof String newDescription) {
            if (newDescription.isEmpty()) {
                return Optional.of("Project description cannot be empty");
            }
            project.setDescription(newDescription);
        } else {
            return Optional.of("Invalid value type for project description. Expected a string");
        }
        return Optional.empty();
    }
}


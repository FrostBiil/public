package dtu.group5.backend.service.project.edit_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

// Made by Elias (s241121)
public class TitleEditor implements ProjectFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "title".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(Project project, Object value) {
        if (value instanceof String newTitle) {
            if (newTitle.isEmpty()) {
                return Optional.of("Project title cannot be empty");
            }
            project.setTitle(newTitle);
        } else {
            return Optional.of("Invalid value type for project title. Expected a string");
        }
        return Optional.empty();
    }
}

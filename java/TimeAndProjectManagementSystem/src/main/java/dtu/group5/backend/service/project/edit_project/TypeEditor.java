package dtu.group5.backend.service.project.edit_project;

import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectType;

import java.util.Optional;

// Made by Elias (s241121)
public class TypeEditor implements ProjectFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "type".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(Project project, Object value) {


        if (value instanceof ProjectType newProjectType) {
            if (newProjectType == null) {
                return Optional.of("Invalid project type");
            }

            project.setType(newProjectType);
        } else {
            return Optional.of("Invalid value type for project type");
        }
        return Optional.empty();

    }
}


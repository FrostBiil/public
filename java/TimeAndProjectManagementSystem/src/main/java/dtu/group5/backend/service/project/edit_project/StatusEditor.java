package dtu.group5.backend.service.project.edit_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

public class StatusEditor implements ProjectFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "status".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(Project project, Object value) {


        if (value instanceof Project.ProjectStatus newProjectStatus) {
            if (newProjectStatus == null) {
                return Optional.of("Invalid project status");
            }

            project.setStatus(newProjectStatus);
        } else {
            return Optional.of("Invalid value type for project status");
        }
        return Optional.empty();

    }
}

package dtu.group5.backend.service.project.edit_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

public interface ProjectFieldEditor {
    boolean supports(String fieldName);
    Optional<String> edit(Project project, Object value);
}

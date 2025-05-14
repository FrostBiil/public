package dtu.group5.backend.service.project.create_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

// Made by Mattias (s245759)
public interface ProjectFieldCreator {
    boolean supports(String fieldName);
    Optional<String> create(Project project, Object value);
}

package dtu.group5.backend.service.project.validator_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

// Made by Matthias (s245759)
public interface ProjectFieldValidator {
    Optional<String> validate(Project project);
}

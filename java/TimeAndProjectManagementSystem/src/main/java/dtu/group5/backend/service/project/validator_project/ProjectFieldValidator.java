package dtu.group5.backend.service.project.validator_project;

import dtu.group5.backend.model.Project;

import java.util.Optional;

public interface ProjectFieldValidator {
    Optional<String> validate(Project project);
}

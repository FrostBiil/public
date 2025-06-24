package dtu.group5.backend.service.project.edit_project;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;

import java.util.Optional;

public class ProjectLeaderEditor implements ProjectFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "project-leader".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(Project project, Object value) {
        if (value instanceof String newCoworkerInitial) {
            if (newCoworkerInitial.isEmpty()) {
                return Optional.of("Coworker Intitial type cannot be null or empty");
            }
            Coworker coworker = project.getCoworkers().stream().filter(c -> c.getInitials().equals(newCoworkerInitial)).findFirst().orElse(null);
            if (coworker == null) {
                return Optional.of("Coworker must be part of the project to be project leader");
            }
            project.setProjectLeader(coworker);
        } else {
            return Optional.of("Invalid value type for project description. Expected a string");
        }
        return Optional.empty();
    }
}

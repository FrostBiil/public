package dtu.group5.backend.service.project.create_project;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;

import java.util.Optional;

// Made by Mattias (s245759)
public class ProjectLeaderCreator implements ProjectFieldCreator {
    @Override
    public boolean supports(String fieldName) {
        return "projectleader".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> create(Project project, Object value) {
        if (value instanceof Coworker leader) {
            project.setProjectLeader(leader);
            return Optional.empty();
        }
        return Optional.of("Invalid project leader");
    }
}

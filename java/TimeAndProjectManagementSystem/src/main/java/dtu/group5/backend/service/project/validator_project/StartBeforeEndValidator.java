package dtu.group5.backend.service.project.validator_project;

import dtu.group5.backend.model.Project;

import java.util.Date;
import java.util.Optional;

public class StartBeforeEndValidator implements ProjectFieldValidator {
    @Override
    public Optional<String> validate(Project project) {
        Date start = project.getStartDate();
        Date end = project.getEndDate();
        if (start != null && end != null && end.before(start)) {
            return Optional.of("End date cannot be before start date");
        }
        return Optional.empty();
    }
}

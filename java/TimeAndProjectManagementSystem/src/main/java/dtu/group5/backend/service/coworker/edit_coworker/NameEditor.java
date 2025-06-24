package dtu.group5.backend.service.coworker.edit_coworker;

import dtu.group5.backend.model.Coworker;

import java.util.Optional;

public class NameEditor implements CoworkerFieldEditor {
    @Override
    public boolean supports(String fieldName) {
        return "name".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(Coworker coworker, Object value) {
        if (value instanceof String newName) {
            if (newName.isEmpty()) {
                return Optional.of("Coworker type cannot be null or empty");
            }
            coworker.setName(newName);
        } else {
            return Optional.of("Invalid value type for coworker name. Expected a string");
        }
        return Optional.empty();
    }
}
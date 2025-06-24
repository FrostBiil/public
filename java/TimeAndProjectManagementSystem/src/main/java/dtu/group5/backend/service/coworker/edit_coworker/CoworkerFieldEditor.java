package dtu.group5.backend.service.coworker.edit_coworker;

import dtu.group5.backend.model.Coworker;

import java.util.Optional;

public interface CoworkerFieldEditor {
    boolean supports(String fieldName);
    Optional<String> edit(Coworker coworker, Object value);
}
package dtu.group5.frontend.controller;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.service.CoworkerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// Made by Matthias(s245759)
public class CoworkerController implements Controller<Coworker> {
  private final CoworkerService coworkerService;

  // Made by Matthias(s245759)
  public CoworkerController(CoworkerService coworkerService) {
    this.coworkerService = coworkerService;
  }

  // Made by Matthias(s245759)
  @Override
  public Optional<String> handleCreate(Coworker coworker) {
    return this.coworkerService.create(coworker);
  }

  // Made by Matthias(s245759)
  @Override
  public Optional<String> handleEdit(Coworker coworker, Map<String, Object> fieldsToChange){
    return this.coworkerService.editCoworker(coworker, fieldsToChange);
  }

  // Made by Elias(s241121)
  @Override
  public Optional<String> handleDelete(Coworker coworker) {
    return this.coworkerService.removeCoworker(coworker);
  }

    // Made by Elias(s241121)
  @Override
  public List<Coworker> getList() {
    return coworkerService.getList();
  }
}

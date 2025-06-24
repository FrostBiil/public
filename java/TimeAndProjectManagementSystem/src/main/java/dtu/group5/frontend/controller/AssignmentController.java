package dtu.group5.frontend.controller;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.AssignmentService;

import java.util.Optional;

public class AssignmentController {
  private final AssignmentService assignmentService;

  public AssignmentController(AssignmentService assignmentService) {
    this.assignmentService = assignmentService;
  }

  public Optional<String> assignMember(ProjectActivity activity, Coworker coworker, boolean force) {
    return this.assignmentService.assignCoworkerToActivity(activity, coworker, force);
  }


  public Optional<String> assignCoworkerToProject(Project project, Coworker coworker) {
    return this.assignmentService.assignCoworkerToProject(project, coworker);
  }
}

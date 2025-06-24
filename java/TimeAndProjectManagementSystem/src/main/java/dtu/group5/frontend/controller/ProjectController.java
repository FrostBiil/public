package dtu.group5.frontend.controller;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ProjectController implements Controller<Project> {
   private final ProjectService projectService;

  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @Override
  public Optional<String> handleCreate(Project project) {
    Map<String, Object> fields = new HashMap<>();
    fields.put("title", project.getTitle());

    return this.projectService.create(fields);
  }

  @Override
  public Optional<String> handleEdit(Project project, Map<String, Object> fieldsToChange) {
    return this.projectService.editProject(project, fieldsToChange);
  }

  @Override
  public Optional<String> handleDelete(Project project) {
    return this.projectService.removeProject(project);
  }

  public Optional<String> removeCoworkerFromProject(Project project, Coworker coworker) {
      return this.projectService.removeCoworkerFromProject(project, coworker);
  }

  public List<String[]> generateWorkloadReport(Project project) {
    return this.projectService.generateWorkloadReport(project);
  }

  public Set<ProjectActivity> getProjectActivities(Project project) {
    return this.projectService.getProjectActivities(project);
  }

  @Override
  public List<Project> getList() {
    return projectService.getList();
  }
}

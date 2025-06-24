package dtu.group5.backend.repository;

import dtu.group5.backend.model.Project;
import dtu.group5.database.DataManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProjectRepository extends BaseRepository<Integer, Project> {
  private static ProjectRepository instance;

  private ProjectRepository() {
    super();
  }

  public static synchronized ProjectRepository getInstance() {
    if (instance == null) {
      instance = new ProjectRepository();
    }
    return instance;
  }

  // Related to the database and is not tested
  public static synchronized void initialize(List<Project> initialData) {
    getInstance().repository.clear();
    for (Project project : initialData) {
      getInstance().repository.put(project.getProjectNumber(), project);
    }
  }

  @Override
  public boolean add(Project project) {
    int number = project.getProjectNumber();
    if (repository.containsKey(number)) return false;
    repository.put(number, project);
    save();
    return true;
  }

  // Related to the database and is not tested
  @Override
  public void save() {
    CompletableFuture.runAsync(() -> {
      if (DataManager.getInstance() == null) return;
      DataManager.getInstance().saveProjects(repository.values().stream().toList());
    });
  }
}

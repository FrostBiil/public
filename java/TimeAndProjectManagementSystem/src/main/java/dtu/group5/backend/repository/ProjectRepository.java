package dtu.group5.backend.repository;

import dtu.group5.backend.model.Project;
import dtu.group5.database.DataManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// Made by Mattias (s245759)
public class ProjectRepository extends BaseRepository<Integer, Project> {
  private static ProjectRepository instance;

  // Made by Mattias (s245759)
  private ProjectRepository() {
    super();
  }

  // Made by Mattias (s245759)
  public static synchronized ProjectRepository getInstance() {
    if (instance == null) {
      instance = new ProjectRepository();
    }
    return instance;
  }

  // Made by Mattias (s245759)
  // Related to the database and is not tested
  public static synchronized void initialize(List<Project> initialData) {
    getInstance().repository.clear();
    for (Project project : initialData) {
      getInstance().repository.put(project.getProjectNumber(), project);
    }
  }

  // Made by Mattias (s245759)
  @Override
  public boolean add(Project project) {
    int number = project.getProjectNumber();
    if (repository.containsKey(number)) return false;
    repository.put(number, project);
    save();
    return true;
  }

  // Made by Elias (241121)
  // Related to the database and is not tested
  @Override
  public void save() {
    CompletableFuture.runAsync(() -> {
      if (DataManager.getInstance() == null) return;
      DataManager.getInstance().saveProjects(repository.values().stream().toList());
    });
  }
}

package dtu.group5.cucumber;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.CoworkerRepository;
import dtu.group5.backend.repository.IRepository;
import dtu.group5.backend.repository.ProjectRepository;

public class RepositoryHolder {
  private static RepositoryHolder instance = null;

  private final IRepository<String, BaseActivity> activityRepository = ActivityRepository.getInstance();
  private final IRepository<String, Coworker> coworkerRepository = CoworkerRepository.getInstance();
  private final IRepository<Integer, Project> projectRepository = ProjectRepository.getInstance();

  public static RepositoryHolder getInstance() {
    if (instance == null) {
      instance = new RepositoryHolder();
    }
    return instance;
  }

  public IRepository<String, BaseActivity> getActivityRepository() {
    return activityRepository;
  }

  public IRepository<String, Coworker> getCoworkerRepository() {
    return coworkerRepository;
  }

  public IRepository<Integer, Project> getProjectRepository() {
    return projectRepository;
  }

  public void clear() {
    activityRepository.clear();
    coworkerRepository.clear();
    projectRepository.clear();
  }
}
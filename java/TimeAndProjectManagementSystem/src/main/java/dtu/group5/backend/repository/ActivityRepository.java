package dtu.group5.backend.repository;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.database.DataManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// Made by Mattias (s245759)
public class ActivityRepository extends BaseRepository<String, BaseActivity> {
  private static ActivityRepository instance;

  // Made by Mattias (s245759)
  private ActivityRepository() {
    super();
  }

  // Made by Mattias (s245759)
  public static synchronized ActivityRepository getInstance() {
    if (instance == null) {
      instance = new ActivityRepository();
    }
    return instance;
  }

  // Made by Mattias (s245759)
  // Related to the database and is not tested
  public static void initialize(List<BaseActivity> initialData) {
    getInstance().clear();
    for (BaseActivity activity : initialData) {
      getInstance().add(activity);
    }
  }

  // Made by Mattias (s245759)
  @Override
  public boolean add(BaseActivity activity) {
    String name = activity.getTitle();
    if (repository.containsKey(name)) {
      return false;
    }
    repository.put(name, activity);
    save();
    return true;
  }

  // Made by Elias (241121)
  // Related to the database and is not tested
  @Override
  public void save() {
    CompletableFuture.runAsync(() -> {
      if (DataManager.getInstance() == null) return;
      DataManager.getInstance().saveActivities(repository.values().stream().toList());
    });
  }
}

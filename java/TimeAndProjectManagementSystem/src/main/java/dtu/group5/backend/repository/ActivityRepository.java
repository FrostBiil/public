package dtu.group5.backend.repository;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.database.DataManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ActivityRepository extends BaseRepository<String, BaseActivity> {
  private static ActivityRepository instance;

  private ActivityRepository() {
    super();
  }

  public static synchronized ActivityRepository getInstance() {
    if (instance == null) {
      instance = new ActivityRepository();
    }
    return instance;
  }

  // Related to the database and is not tested
  public static void initialize(List<BaseActivity> initialData) {
    getInstance().clear();
    for (BaseActivity activity : initialData) {
      getInstance().add(activity);
    }
  }

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

  // Related to the database and is not tested
  @Override
  public void save() {
    CompletableFuture.runAsync(() -> {
      if (DataManager.getInstance() == null) return;
      DataManager.getInstance().saveActivities(repository.values().stream().toList());
    });
  }
}

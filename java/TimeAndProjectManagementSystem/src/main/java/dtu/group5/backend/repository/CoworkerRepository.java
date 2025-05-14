package dtu.group5.backend.repository;

import dtu.group5.backend.model.Coworker;
import dtu.group5.database.DataManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;

// Made by Mattias (s245759)
public class CoworkerRepository extends BaseRepository<String, Coworker> {
  private static CoworkerRepository instance;

  // Made by Mattias (s245759)
  private CoworkerRepository() { super(); }

  // Made by Mattias (s245759)
  public static synchronized CoworkerRepository getInstance() {
    if (instance == null) {
      instance = new CoworkerRepository();
    }
    return instance;
  }

  // Made by Mattias (s245759)
  // Related to the database and is not tested
  public static void initialize(List<Coworker> initialData) {
    getInstance().clear();
    for (Coworker c : initialData) {
      getInstance().add(c);
    }
  }

  // Made by Mattias (s245759)
  @Override
  public boolean add(Coworker coworker) {
    String initials = coworker.getInitials();
    if (repository.containsKey(initials)) {
      return false;
    }
    repository.put(initials, coworker);
    save();
    return true;
  }

  // Made by Elias (241121)
  // Related to the database and is not tested
  @Override
  public void save() {
    CompletableFuture.runAsync(() -> {
      if (DataManager.getInstance() == null) return;
      DataManager.getInstance().saveCoworkers(repository.values().stream().toList());
    });
  }
}

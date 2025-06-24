package dtu.group5.frontend.session;

import dtu.group5.backend.model.Coworker;
import dtu.group5.database.DataManager;

import java.util.List;
import java.util.Optional;

public class Session {
  private static Session instance = new Session();
  private Coworker loggedInUser;

  private Session() {}

  public static Session getInstance() {
    return instance;
  }

  public Coworker getLoggedInUser() {
    return loggedInUser;
  }

  public void setLoggedInUser(Coworker user) {
    this.loggedInUser = user;
  }

  public boolean isLoggedIn() {
    return loggedInUser != null;
  }

  public void logout() {
    loggedInUser = null;
  }

  public static Optional<String> login(String initials) {
    List<Coworker> coworkers = DataManager.getInstance().loadCoworkers();

    Optional<Coworker> loggedInUser = coworkers.stream().filter(c -> {
      return c.getInitials().equals(initials);
    }).findFirst();

    if (loggedInUser.isEmpty()) {
      return Optional.of("Unknown initials: " + initials);
    }

    Session.getInstance().setLoggedInUser(loggedInUser.get());
    return Optional.empty();
  }
}

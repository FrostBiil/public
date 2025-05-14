package dtu.group5.frontend.session;

import dtu.group5.backend.model.Coworker;
import dtu.group5.database.DataManager;

import java.util.List;
import java.util.Optional;

// Made by Matthias(s245759)
public class Session {
  private static Session instance = new Session();
  private Coworker loggedInUser;

  // Made by Matthias(s245759)
  private Session() {}

  // Made by Matthias(s245759)
  public static Session getInstance() {
    return instance;
  }

  // Made by Matthias(s245759)
  public Coworker getLoggedInUser() {
    return loggedInUser;
  }

  // Made by Matthias(s245759)
  public void setLoggedInUser(Coworker user) {
    this.loggedInUser = user;
  }

  // Made by Elias(s241121)
  public boolean isLoggedIn() {
    return loggedInUser != null;
  }

  // Made by Elias(s241121)
  public void logout() {
    loggedInUser = null;
  }

  // Made by Matthias(s245759)
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

package dtu.group5.cucumber;

import dtu.group5.frontend.session.Session;
import io.cucumber.java.Before;

public class Hooks {
    private final RepositoryHolder repository = RepositoryHolder.getInstance();
    private final ErrorMessageHolder errorMessageHolder = ErrorMessageHolder.getInstance();
    private final Session session = Session.getInstance();

    @Before
    public void resetSystemState() {
        repository.clear();
        errorMessageHolder.clear();
        session.logout();
    }
}

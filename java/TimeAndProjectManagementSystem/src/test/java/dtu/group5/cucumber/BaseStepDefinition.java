package dtu.group5.cucumber;

public abstract class BaseStepDefinition {
  protected final RepositoryHolder repository = RepositoryHolder.getInstance();
  protected final ServiceHolder services = ServiceHolder.getInstance();
  protected final ErrorMessageHolder errorMessage = ErrorMessageHolder.getInstance();
}

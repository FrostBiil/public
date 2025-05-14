package dtu.group5.cucumber;

import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import io.cucumber.java.en.When;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinishActivitySteps extends BaseStepDefinition {
  @When("the project {int} activity {string} is marked as finished")
  public void theProjectActivityIsMarkedAsFinished(int projectNumber, String title) {

    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    Optional<ProjectActivity> activityOpt = services.getProjectService().getProjectActivities(projectOpt.get()).stream()
      .filter(a -> a.getTitle().equals(title))
      .findFirst();
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title + " in project " + projectNumber);

    Optional<String> error = services.getActivityService().markAsFinished(activityOpt.get());
    error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
  }

}

package dtu.group5.cucumber;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignCoworkerToActivitySteps extends BaseStepDefinition {

  @Given("coworker with initials {string} is assigned to activities")
  public void coworkerWithInitialsIsAssignedToActivities(String initials, DataTable dataTable) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
    assertTrue(coworkerOpt.isPresent(), "Coworker with initials " + initials + " not found");
    Coworker coworker = coworkerOpt.get();

    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      int projectNumber = Integer.parseInt(row.get("Project Number"));
      String activityTitle = row.get("Activity Title");

      Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
      assertTrue(projectOpt.isPresent(), "Project " + projectNumber + " not found");

      Set<ProjectActivity> activities = services.getProjectService().getProjectActivities(projectOpt.get());
      Optional<ProjectActivity> activityOpt = activities.stream()
        .filter(a -> a.getTitle().equals(activityTitle))
        .findFirst();

      assertTrue(activityOpt.isPresent(), "Activity '" + activityTitle + "' not found in project " + projectNumber);

      Optional<String> response = services.getAssignmentService().assignCoworkerToActivity(activityOpt.get(), coworker, false);
      response.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);

      assertTrue(activityOpt.get().isCoworkerAssigned(coworker), "Coworker not assigned to activity: " + activityTitle);
    }
  }


  @Given("coworker with initials {string} tries to be assigned to activities")
  public void coworkerWithInitialsTriesToBeAssignedToActivities(String string, DataTable dataTable) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(string);
    assertTrue(coworkerOpt.isPresent(), "Coworker with initials " + string + " not found");
    Coworker coworker = coworkerOpt.get();


    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      String title = row.get("Activity Title");

      Optional<BaseActivity> activityOpt = services.getActivityService().get(title);

      assertTrue(activityOpt.isPresent(), "Activity with title " + title + " not found");

      Optional<String> response = services.getAssignmentService().assignCoworkerToActivity(activityOpt.get(), coworker, false);

      response.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    }
  }

  @Given("the activity {string} exists in project {int}")
  public void theActivityExistsInProject(String activityTitle, int projectNumber) {
    Optional<Project> project = services.getProjectService().get(projectNumber);
    assertTrue(project.isPresent(), "Project " + projectNumber + " not found");

    Optional<ProjectActivity> activity = services.getProjectService().getProjectActivities(project.get()).stream()
        .filter(a -> a.getTitle().equals(activityTitle))
        .findFirst();
    assertTrue(activity.isPresent(), "Activity " + activityTitle + " not found in project " + projectNumber);
  }

  @When("coworker with initials {string} is assigned to activity {string} in project {int}")
  public void assignCoworkerToActivity(String initials, String activityTitle, int projectNumber) {
    try {
      Coworker coworker = services.getCoworkerService()
          .get(initials)
          .orElseThrow(() -> new RuntimeException("Coworker " + initials + " not found"));

      Project project = services.getProjectService()
          .get(projectNumber)
          .orElseThrow(() -> new RuntimeException("Project " + projectNumber + " not found"));

      ProjectActivity activity = services.getProjectService().getProjectActivities(project).stream()
          .filter(a -> a.getTitle().equals(activityTitle))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Activity " + activityTitle + " not found"));

      Optional<String> error = services
          .getAssignmentService()
          .assignCoworkerToActivity(activity, coworker, false);

      error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);

    } catch (Exception e) {
      ErrorMessageHolder.getInstance().setErrorMessage(e.getMessage());
    }
  }

  @Given("the activity {string} in project {int} already has coworker {string} assigned")
  public void theActivityAlreadyHasCoworkerAssigned(String activityTitle, int projectNumber, String initials) {
    assignCoworkerToActivity(initials, activityTitle, projectNumber);
  }

  @When("coworker {string} is assigned to {string}")
  public void coworkerIsAssignedTo(String initials, String targetActivityTitle) {
    assignCoworkerToActivity(initials, targetActivityTitle, 25001);
  }

  @Then("the following coworkers are assigned to activity {string}")
  public void theFollowingCoworkersAreAssignedToActivity(String arg0, DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      String initials = row.get("Initials");

      Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
      assertTrue(coworkerOpt.isPresent(), "Coworker with initials " + initials + " not found");
      Coworker coworker = coworkerOpt.get();

      Optional<BaseActivity> activityOpt = services.getActivityService().get(arg0);
      assertTrue(activityOpt.isPresent(), "Activity with title " + arg0 + " not found");

      assertTrue(activityOpt.get().isCoworkerAssigned(coworker), "Coworker " + initials + " is not assigned to activity " + arg0);
    }
  }
}

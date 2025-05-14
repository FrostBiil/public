package dtu.group5.cucumber;

import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateActivityForProjectSteps extends BaseStepDefinition{
  @When("a new activity with the following details")
  public void aNewActivityWithTheFollowingDetailsIsCreatedForProject(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
      int projectNumber = Integer.parseInt(row.get("Project Number"));

      Optional<String> error = services.getActivityService().createProductActivity(
        projectNumber,
        row.get("Activity Title"),
        Double.parseDouble(row.get("Expected Hours")),
        Boolean.parseBoolean(row.get("Finished")),
        row.get("Description"),
        new HashSet<>(),
        Integer.parseInt(row.get("Start Year")),
        Integer.parseInt(row.get("Start Week Number")),
        Integer.parseInt(row.get("End Year")),
        Integer.parseInt(row.get("End Week Number")),
        new HashMap<>()
      );

      error.ifPresent(errorMessage::setErrorMessage);
    }
  }

  @Then("the project {int} should contain the following activities")
  public void theProjectShouldContainTheFollowingActivities(int projectNumber, DataTable dataTable) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    List<Map<String, String>> expectedActivities = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> expected : expectedActivities) {
      String title = expected.get("Activity Title");

      boolean found = services.getActivityService().getList().stream()
              .filter(a -> a instanceof ProjectActivity)
              .map(a -> (ProjectActivity) a)
              .anyMatch(a -> a.getTitle().equals(title) && a.getProjectNumber() == projectNumber);

      assertTrue(found, "Expected activity '" + title + "' not found in project " + projectNumber);
    }
  }
}

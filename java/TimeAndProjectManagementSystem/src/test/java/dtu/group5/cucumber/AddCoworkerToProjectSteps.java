package dtu.group5.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;


public class AddCoworkerToProjectSteps extends BaseStepDefinition {
  @When("coworker with initials {string} is added to project {int}")
  public void coworkerIsAddedToProject(String initials, int projectNumber) {
    try {
      var coworker = ServiceHolder.getInstance().getCoworkerService().get(initials)
              .orElseThrow(() -> new RuntimeException("Coworker " + initials + " not found"));

      var project = ServiceHolder.getInstance().getProjectService().get(projectNumber)
              .orElseThrow(() -> new RuntimeException("Project " + projectNumber + " not found"));

      var error = ServiceHolder.getInstance().getAssignmentService().assignCoworkerToProject(project, coworker);
      error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    } catch (Exception e) {
      ErrorMessageHolder.getInstance().setErrorMessage(e.getMessage());
    }
  }

  @When("the following coworkers are added to project {int}")
  public void multipleCoworkersAreAddedToProject(int projectNumber, DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
      coworkerIsAddedToProject(row.get("Initials"), projectNumber);
    }
  }

  @Given("coworker with initials {string} is already added to project {int}")
  public void coworkerAlreadyAddedToProject(String initials, int projectNumber) {
    coworkerIsAddedToProject(initials, projectNumber);
  }

  @When("coworker with initials {string} is added again to project {int}")
  public void coworkerAddedAgainToProject(String initials, int projectNumber) {
    coworkerIsAddedToProject(initials, projectNumber);
  }
}

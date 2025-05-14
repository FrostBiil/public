package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditProjectSteps extends BaseStepDefinition {
  @When("the project title of {int} is changed to {string}")
  public void theProjectTitleIsChanged(int projectNumber, String newTitle) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    Optional<String> error = services.getProjectService().editProject(projectOpt.get(), Map.of("title", newTitle));
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @When("the project description of {int} is changed to {string}")
  public void theProjectDescriptionIsChanged(int projectNumber, String newDescription) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    Optional<String> error = services.getProjectService().editProject(projectOpt.get(), Map.of("description", newDescription));
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @When("the project start date of {int} is changed to {string}")
  public void theProjectStartDateIsChanged(int projectNumber, String newStartDate) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    Optional<String> error = services.getProjectService().editProject(projectOpt.get(), Map.of("startDate", newStartDate));
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @When("the project end date of {int} is changed to {string}")
  public void theProjectEndDateIsChanged(int projectNumber, String newEndDate) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    Optional<String> error = services.getProjectService().editProject(projectOpt.get(), Map.of("endDate", newEndDate));
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @When("the project type of {int} is changed to {string}")
  public void theProjectTypeIsChanged(int projectNumber, String newType) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    Optional<String> error = services.getProjectService().editProject(projectOpt.get(), Map.of("type", newType));
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @When("the project status of {int} is changed to {string}")
  public void theProjectStatusIsChanged(int projectNumber, String newStatus) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    Optional<String> error = services.getProjectService().editProject(projectOpt.get(), Map.of("status", newStatus));
    error.ifPresent(errorMessage::setErrorMessage);
  }


  @Given("coworker with initials {string} is assigned to projects")
  public void coworkerWithInitialsIsAssignedToProjects(String initials, io.cucumber.datatable.DataTable dataTable) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
    assertTrue(coworkerOpt.isPresent(), "Coworker with initials " + initials + " not found");
    Coworker coworker = coworkerOpt.get();


    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      int projectNumber = Integer.parseInt(row.get("Project Number"));

      Optional<Project> projectOpt = services.getProjectService().get(projectNumber);

      assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

      services.getAssignmentService().assignCoworkerToProject(projectOpt.get(), coworker);

      Optional<Project> updatedProjectOpt = services.getProjectService().get(projectNumber);
      assertTrue(updatedProjectOpt.isPresent(), "Project not found after assignment: " + projectNumber);
      assertTrue(updatedProjectOpt.get().getCoworkers().stream().anyMatch(c -> c.getInitials().equals(coworker.getInitials())));
    }
  }

  @Then("project {int} has the following coworkers")
  public void projectHasTheFollowingCoworkers(int projectNumber, io.cucumber.datatable.DataTable dataTable) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);

    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      String intials = row.get("Initials");

      assertTrue(projectOpt.get().getCoworkers().stream().anyMatch(c -> c.getInitials().equals(intials)), "Coworker with initials " + intials + " not found in project " + projectNumber);
    }
  }

  @When("coworker with initials {string} is removed from project {int}")
  public void coworkerWithInitialsIsRemovedFromProject(String initials, int projectNumber) {

    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);

    assertTrue(coworkerOpt.isPresent(), "Coworker with initials " + initials + " not found");

    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);

    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    assertTrue(projectOpt.get().getCoworkers().stream().anyMatch(c -> c.getInitials().equals(initials)), "Coworker with initials " + initials + " not found in project " + projectNumber);

    this.services.getProjectService().removeCoworkerFromProject(projectOpt.get(), coworkerOpt.get());
  }
}

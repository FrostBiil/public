package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.frontend.session.Session;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListAssignedProjectsSteps extends BaseStepDefinition {
  private List<Project> displayedProjects;

  @When("coworker {string} lists their projects")
  public void coworkerListsTheirProjects(String initials) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
    if (coworkerOpt.isEmpty()) {
      errorMessage.setErrorMessage("Coworker not found");
      return;
    }

    Coworker coworker = coworkerOpt.get();
    Session.getInstance().setLoggedInUser(coworker);

    displayedProjects = services.getProjectService().getList().stream()
      .filter(project -> project.getCoworkers().contains(coworker))
      .toList();
  }

  @Then("the following projects are displayed")
  public void theFollowingProjectsAreDisplayed(DataTable dataTable) {
    List<List<String>> expectedRows = dataTable.asLists(String.class);

    // Fjern header
    expectedRows = expectedRows.subList(1, expectedRows.size());

    for (List<String> expectedRow : expectedRows) {
      String expectedNumber = expectedRow.get(0);
      String expectedTitle = expectedRow.get(1);

      boolean match = displayedProjects.stream().anyMatch(project ->
        String.valueOf(project.getProjectNumber()).equals(expectedNumber) &&
          project.getTitle().equals(expectedTitle)
      );

      assertTrue(match, "Expected project not found: " + expectedNumber + " - " + expectedTitle);
    }

    assertEquals(expectedRows.size(), displayedProjects.size(), "Mismatch in number of displayed projects");
  }
}

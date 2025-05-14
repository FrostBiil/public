//package dtu.group5.cucumber;
//
//import dtu.group5.backend.model.Coworker;
//import dtu.group5.backend.model.Project;
//import dtu.group5.frontend.session.Session;
//import io.cucumber.datatable.DataTable;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ViewPersonalProjectOverviewSteps extends BaseStepDefinition {
//  @When("coworker {string} checks their uncompleted projects")
//  public void coworkerChecksTheirUncompletedProjects(String initials) {
//    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
//    if (coworkerOpt.isEmpty()) {
//      errorMessage.setErrorMessage("Coworker not found");
//      return;
//    }
//    List<Project> projects = services.getProjectService().getUncompletedProjectsForCoworker(coworkerOpt.get());
//    Session.getInstance().setCurrentProjects(projects);
//  }
//
//  @Then("the following uncompleted projects are displayed")
//  public void theFollowingUncompletedProjectsAreDisplayed(DataTable dataTable) {
//    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
//    List<Project> projects = Session.getInstance().getCurrentProjects();
//    assertEquals(rows.size(), projects.size(), "Mismatch in number of projects displayed");
//
//    for (Map<String, String> row : rows) {
//      int projectNumber = Integer.parseInt(row.get("Project Number"));
//      String title = row.get("Title");
//
//      Project project = projects.stream()
//        .filter(p -> p.getProjectNumber() == projectNumber)
//        .findFirst()
//        .orElseThrow(() -> new AssertionError("Project not found: " + projectNumber));
//
//      assertEquals(title, project.getTitle(), "Mismatch in project title for project number: " + projectNumber);
//    }
//  }
//
//  @Given("all projects assigned to coworker {string} are marked as finished")
//  public void allProjectsAssignedToCoworkerAreMarkedAsFinished(String initials) {
//    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
//    assertTrue(coworkerOpt.isPresent(), "Coworker not found: " + initials);
//
//    List<Project> projects = services.getProjectService().getProjectsForCoworker(coworkerOpt.get());
//    projects.forEach(project -> project.getActivities().forEach(activity -> activity.setFinished(true)));
//  }
//
//  @Given("coworker {string} does not exist")
//  public void coworkerDoesNotExist(String initials) {
//    assertTrue(services.getCoworkerService().get(initials).isEmpty(), "Coworker unexpectedly exists: " + initials);
//  }
//
//  @Then("the message {string} is shown")
//  public void theMessageIsShown(String expectedMessage) {
//    assertEquals(expectedMessage, Session.getInstance().getLastDisplayedMessage());
//  }
//
//}

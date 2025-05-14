//package dtu.group5.cucumber;
//
//import dtu.group5.backend.model.Activity;
//import dtu.group5.backend.model.Coworker;
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
//public class ViewPersonalActivityOverviewSteps extends BaseStepDefinition {
//  @When("coworker {string} checks their uncompleted activities")
//  public void coworkerChecksTheirUncompletedActivities(String initials) {
//    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
//    if (coworkerOpt.isEmpty()) {
//      errorMessage.setErrorMessage("Coworker not found");
//      return;
//    }
//    List<Activity> activities = services.getActivityService().getUncompletedActivitiesForCoworker(coworkerOpt.get());
//    Session.getInstance().setCurrentActivities(activities);
//  }
//
//  @Then("the following uncompleted activities are displayed")
//  public void theFollowingUncompletedActivitiesAreDisplayed(DataTable dataTable) {
//    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
//    List<Activity> activities = Session.getInstance().getCurrentActivities();
//    assertEquals(rows.size(), activities.size(), "Mismatch in number of activities displayed");
//
//    for (Map<String, String> row : rows) {
//      String title = row.get("Activity Title");
//      int assignedHours = Integer.parseInt(row.get("Assigned Hours"));
//
//      Activity activity = activities.stream()
//        .filter(a -> a.getTitle().equals(title))
//        .findFirst()
//        .orElseThrow(() -> new AssertionError("Activity not found: " + title));
//
//      int actualHours = services.getActivityService().getRegisteredHours(activity, Session.getInstance().getLoggedInUser().getInitials());
//      assertEquals(assignedHours, actualHours, "Mismatch in assigned hours for activity: " + title);
//    }
//  }
//
//  @Given("all activities assigned to coworker {string} are marked as finished")
//  public void allActivitiesAssignedToCoworkerAreMarkedAsFinished(String initials) {
//    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
//    assertTrue(coworkerOpt.isPresent(), "Coworker not found: " + initials);
//
//    List<Activity> activities = services.getActivityService().getUncompletedActivitiesForCoworker(coworkerOpt.get());
//    activities.forEach(activity -> activity.setFinished(true));
//  }
//
//  @Then("the message {string} is shown")
//  public void theMessageIsShown(String expectedMessage) {
//    assertEquals(expectedMessage, Session.getInstance().getLastDisplayedMessage());
//  }
//
//  @When("coworker {string} checks assigned hours on their activities")
//  public void coworkerChecksAssignedHoursOnTheirActivities(String initials) {
//    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
//    if (coworkerOpt.isEmpty()) {
//      errorMessage.setErrorMessage("Coworker not found");
//      return;
//    }
//    List<Activity> activities = services.getActivityService().getActivitiesWithHours(coworkerOpt.get());
//    Session.getInstance().setCurrentActivities(activities);
//  }
//
//  @Then("the following activities are displayed")
//  public void theFollowingActivitiesAreDisplayed(DataTable dataTable) {
//    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
//    List<Activity> activities = Session.getInstance().getCurrentActivities();
//    assertEquals(rows.size(), activities.size(), "Mismatch in number of activities displayed");
//
//    for (Map<String, String> row : rows) {
//      String title = row.get("Activity Title");
//      int registeredHours = Integer.parseInt(row.get("Registered Hours"));
//
//      Activity activity = activities.stream()
//        .filter(a -> a.getTitle().equals(title))
//        .findFirst()
//        .orElseThrow(() -> new AssertionError("Activity not found: " + title));
//
//      int actualHours = services.getActivityService().getRegisteredHours(activity, Session.getInstance().getLoggedInUser().getInitials());
//      assertEquals(registeredHours, actualHours, "Mismatch in registered hours for activity: " + title);
//    }
//  }
//
//  @Given("coworker {string} does not exist")
//  public void coworkerDoesNotExist(String initials) {
//    assertTrue(services.getCoworkerService().get(initials).isEmpty(), "Coworker unexpectedly exists: " + initials);
//  }
//
//}

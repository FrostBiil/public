package dtu.group5.cucumber;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.session.Session;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static dtu.group5.backend.util.DateUtil.stripTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListAssignedActivitiesSteps extends BaseStepDefinition {

  private List<BaseActivity> visibleActivities;

  @When("coworker {string} checks their activities")
  public void coworkerChecksTheirActivities(String initials) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
    if (coworkerOpt.isEmpty()) {
      errorMessage.setErrorMessage("Coworker not found");
      visibleActivities = Collections.emptyList();
      return;
    }

    Coworker coworker = coworkerOpt.get();
    Session.getInstance().setLoggedInUser(coworker);

    visibleActivities = services.getActivityService().getList().stream()
      .filter(a -> a.getAssignedCoworkers().contains(coworker))
      .filter(a -> !a.isFinished())
      .collect(Collectors.toList());
  }

  @Then("the following activities are displayed")
  public void theFollowingActivitiesAreDisplayed(DataTable dataTable) {
    List<List<String>> expected = dataTable.asLists();
    List<List<String>> rows = expected.subList(1, expected.size());

    Coworker loggedIn = Session.getInstance().getLoggedInUser();
    assertNotNull(loggedIn, "No coworker is logged in");

    Date today = stripTime(new Date());

    List<String[]> workedHours = services.getWorkedHoursService()
      .getWorkedHoursByCoworker(loggedIn.getInitials(), today);

    for (List<String> expectedRow : rows) {
      String expectedTitle = expectedRow.get(0);
      double expectedHours = Double.parseDouble(expectedRow.get(1));

      Optional<BaseActivity> match = visibleActivities.stream()
        .filter(a -> a.getTitle().equals(expectedTitle))
        .findFirst();

      assertTrue(match.isPresent(), "Missing expected activity: " + expectedTitle);

      double actualHours = workedHours.stream()
        .filter(row -> row[1].equals(expectedTitle))  // Titel matcher
        .mapToDouble(row -> Double.parseDouble(row[2])) // Timer
        .sum();

      assertEquals(expectedHours, actualHours, 0.001,
        "Mismatch in hours for activity: " + expectedTitle);
    }

    assertEquals(rows.size(), visibleActivities.size(),
      "Mismatch in number of displayed activities");
  }

}
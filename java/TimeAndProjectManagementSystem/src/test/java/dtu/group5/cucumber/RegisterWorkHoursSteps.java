package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.session.Session;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.formatDate;
import static dtu.group5.backend.util.DateUtil.stripTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterWorkHoursSteps extends BaseStepDefinition {
  @When("coworker {string} attempts to register {int} hours to project {int} activity {string}")
  public void attemptToRegisterOnFinished(String initials, int hours, int projectNumber, String activityTitle) {
    Coworker coworker = services.getCoworkerService().get(initials)
      .orElseThrow(() -> new RuntimeException("Coworker " + initials + " not found"));
    Session.getInstance().setLoggedInUser(coworker);

    Optional<String> error = services.getWorkedHoursService()
      .registerWorkedHours(projectNumber, activityTitle, hours);

    error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
  }

  @When("coworker {string} attempts to register {int} hours")
  public void attemptToRegisterNegativeTime(String initials, int hours) {
    Coworker coworker = services.getCoworkerService().get(initials)
      .orElseThrow(() -> new RuntimeException("Coworker " + initials + " not found"));
    Session.getInstance().setLoggedInUser(coworker);

    Optional<String> error = services.getWorkedHoursService()
      .registerWorkedHours(0, "Dummy", hours); // "Dummy" activity assumed invalid
    error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
  }

  @Then("the activity {string} has {double} hours registered hours from coworker {string}")
  public void theActivityHasRegisteredHoursFromCoworker(String activityTitle, double expectedHours, String initials) {
    Coworker coworker = services.getCoworkerService().get(initials)
      .orElseThrow(() -> new RuntimeException("Coworker " + initials + " not found"));
    Session.getInstance().setLoggedInUser(coworker);

    Date today = stripTime(new Date());
    String todayStr = formatDate(today);

    List<String[]> workedHours = services.getWorkedHoursService()
      .getWorkedHoursByCoworker(initials, today);

    double actual = workedHours.stream()
      .filter(row -> row[1].equals(activityTitle))
      .mapToDouble(row -> Double.parseDouble(row[2]))
      .findFirst()
      .orElse(0.0);

    assertEquals(expectedHours, actual, 0.001,
      "Expected " + expectedHours + "h, but got " + actual + "h for coworker " + initials);
  }



  @And("coworker {string} registers work hours")
  public void coworkerRegistersWorkHours(String initials, DataTable table) {
    Coworker coworker = services.getCoworkerService().get(initials)
      .orElseThrow(() -> new RuntimeException("Coworker not found: " + initials));
    Session.getInstance().setLoggedInUser(coworker);

    List<Map<String, String>> rows = table.asMaps();

    for (Map<String, String> row : rows) {
      int projectNumber = Integer.parseInt(row.get("Project Number"));
      String activityTitle = row.get("Activity Title");
      double hours = Double.parseDouble(row.get("Hours"));

      Optional<String> error = services.getWorkedHoursService().registerWorkedHours(projectNumber, activityTitle, hours);

      error.ifPresentOrElse(
        msg -> ErrorMessageHolder.getInstance().setErrorMessage(msg),
        () -> ErrorMessageHolder.getInstance().clear()
      );
    }
  }

}

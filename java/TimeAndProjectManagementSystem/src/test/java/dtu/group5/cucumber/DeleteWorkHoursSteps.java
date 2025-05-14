package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.session.Session;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.stripTime;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteWorkHoursSteps extends BaseStepDefinition {
  @When("coworker {string} deletes worked hours today")
  public void coworkerDeletesWorkedHoursToday(String initals, DataTable dataTable) {
    Coworker coworker = services.getCoworkerService().get(initals)
      .orElseThrow(() -> new RuntimeException("Coworker " + initals + " not found"));
    Session.getInstance().setLoggedInUser(coworker);

    dataTable.asMaps().forEach(map -> {
      int projectNumber = Integer.parseInt(map.get("Project Number"));
      String activityTitle = map.get("Activity Title");

      Date date = stripTime(new Date());

      Optional<String> error = services.getWorkedHoursService()
        .deleteWorkedHours(projectNumber, activityTitle, date);

      error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    });
  }

  @Then("the project {int} activity {string} has no registered hours from coworker {string} today")
  public void theActivityHasNoRegisteredHoursFromCoworkerToday(int projectNumber, String activityTitle, String initials) {
    Date today = stripTime(new Date());
    List<String[]> workedHours = services.getWorkedHoursService()
      .getWorkedHoursByCoworker(initials, today);

    boolean match = workedHours.stream().anyMatch(row ->
      Integer.parseInt(row[0]) == projectNumber &&
        row[1].equals(activityTitle)
    );

    if (match) {
      throw new AssertionError("Coworker " + initials + " has registered hours today on activity " + activityTitle);
    }
  }
}

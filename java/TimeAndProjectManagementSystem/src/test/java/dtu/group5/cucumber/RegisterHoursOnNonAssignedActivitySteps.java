package dtu.group5.cucumber;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.ProjectActivity;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.stripTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RegisterHoursOnNonAssignedActivitySteps extends BaseStepDefinition {

  @When("coworker {string} registers {int} hours and {int} minutes on activity {string}")
  public void coworkerRegistersTimeOnActivity(String initials, int hours, int minutes, String activityTitle) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);
    Optional<BaseActivity> activityOpt = services.getActivityService().findActivityByTitle(activityTitle);

    assertTrue(coworkerOpt.isPresent(), () -> "Coworker not found: " + initials);
    assertTrue(activityOpt.isPresent(), () -> "Activity not found: " + activityTitle);

    BaseActivity activity = activityOpt.get();
    int projectNumber;

    if (activity instanceof ProjectActivity projectActivity) {
      projectNumber = projectActivity.getProjectNumber();
    } else {
      fail("Only project activities support registering worked hours");
      return;
    }

    Optional<String> error = services.getWorkedHoursService()
            .registerWorkedHours(projectNumber, activityTitle, hours);

    error.ifPresent(errorMessage::setErrorMessage);
  }


  @Then("{double} hours are registered for {string} on activity {string}")
  public void timeIsRegisteredForCoworkerOnActivity(double expectedHours, String initials, String activityTitle) {
    Optional<BaseActivity> activityOpt = services.getActivityService().findActivityByTitle(activityTitle);
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(initials);

    assertTrue(activityOpt.isPresent(), "Activity not found: " + activityTitle);
    assertTrue(coworkerOpt.isPresent(), "Coworker not found: " + initials);

    BaseActivity activity = activityOpt.get();
    assertInstanceOf(ProjectActivity.class, activity, "Only project activities support registering worked hours");

    ProjectActivity projectActivity = (ProjectActivity) activity;
    Coworker coworker = coworkerOpt.get();

    Date today = stripTime(new Date());
    Map<Date, Double> timeMap = projectActivity.getWorkedHoursPerCoworker().getOrDefault(coworker, Map.of());

    assertFalse(timeMap.isEmpty(), "No worked hours registered for coworker: " + initials);
    assertTrue(timeMap.containsKey(today), "No worked hours registered for today");

    double actualHours = timeMap.get(today);
    assertEquals(expectedHours, actualHours, "Registered hours mismatch for today");
  }
}

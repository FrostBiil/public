package dtu.group5.cucumber;

import io.cucumber.java.en.When;

import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.getCurrentDate;

public class EditWorkedHoursSteps extends BaseStepDefinition {
  @When("coworker {string} edits {int} hours of work in project {int} activity {string}")
  public void coworkerEditsHoursOfWorkInProjectActivity(String initials, int hours, int projectNumber, String activityTitle) {
    Optional<String> error = services.getWorkedHoursService()
            .editWorkedHours(projectNumber, activityTitle, initials, "workedHours", hours, getCurrentDate());
    error.ifPresent(s -> ErrorMessageHolder.getInstance().setErrorMessage(s));
  }
}

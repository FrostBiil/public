package dtu.group5.cucumber;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.ProjectActivity;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.parseDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditActivitySteps extends BaseStepDefinition{
  @Then("the activity has title {string}")
  public void theActivityHasTitle(String expectedTitle) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(expectedTitle);
    assertTrue(activityOpt.isPresent(), "Activity not found with title: " + expectedTitle);
  }

  @Then("the project activity {string} has description {string}")
  public void theActivityHasDescription(String title, String expectedDescription) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);

    assertEquals(expectedDescription, activityOpt.get().getDescription(), "Incorrect description");
  }

  @Then("the project activity {string} is finished")
  public void theActivityIsFinished(String title) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);

    assertTrue(activityOpt.get().isFinished(), "Activity is not finished");
  }

  @Then("the project activity {string} is not finished")
  public void theActivityIsNotFinished(String title) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);

    assertFalse(activityOpt.get().isFinished(), "Activity is finished");
  }

  @When("the activity {string} start date is changed to {string}")
  public void theActivityStartDateIsChangedTo(String title, String newStartDate) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);

    Map<String, Object> data = Map.of("startDate", newStartDate);
    Optional<String> error = services.getActivityService().editActivity(activityOpt.get(), data);
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @Then("the activity has start date {string}")
  public void theActivityHasStartDate(String expectedStartDate) {
    Optional<BaseActivity> activityOpt = services.getActivityService().getList().stream()
      .filter(a -> a.getStartDate().equals(parseDate(expectedStartDate)))
      .findFirst();
    assertTrue(activityOpt.isPresent(), "Activity with start date " + expectedStartDate + " not found");
  }

  @Then("the activity has end date {string}")
  public void theActivityHasEndDate(String expectedEndDate) {
    Optional<BaseActivity> activityOpt = services.getActivityService().getList().stream()
            .filter(a -> a.getEndDate().equals(parseDate(expectedEndDate)))
            .findFirst();
    assertTrue(activityOpt.isPresent(), "Activity with end date " + expectedEndDate + " not found");
  }

  @When("the activity {string} end date is changed to {string}")
  public void theActivityEndDateIsChangedTo(String title, String newEndDate) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);

    Map<String, Object> data = Map.of("endDate", newEndDate);
    Optional<String> error = services.getActivityService().editActivity(activityOpt.get(), data);
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @Then("the project activity {string} has start year {int}")
  public void theProjectActivityHasStartYear(String arg0, int arg1) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(arg0);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + arg0);
    assertInstanceOf(ProjectActivity.class, activityOpt.get());
    int actual = ((ProjectActivity) activityOpt.get()).getStartYear();
    assertEquals(arg1, actual, "Incorrect start year");
  }

  @Then("the project activity {string} has start week number {int}")
  public void theProjectActivityHasStartWeekNumber(String title, int expectedWeek) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);
    assertInstanceOf(ProjectActivity.class, activityOpt.get());
    int actual = ((ProjectActivity) activityOpt.get()).getStartWeekNumber();
    assertEquals(expectedWeek, actual, "Incorrect start week number");
  }


  @Then("the project activity {string} has end year {int}")
  public void theProjectActivityHasEndYear(String title, int expectedYear) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);
    assertInstanceOf(ProjectActivity.class, activityOpt.get());
    int actual = ((ProjectActivity) activityOpt.get()).getEndYear();
    assertEquals(expectedYear, actual, "Incorrect end year");
  }


  @Then("the project activity {string} has end week number {int}")
  public void theProjectActivityHasEndWeekNumber(String title, int expectedWeek) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);
    assertInstanceOf(ProjectActivity.class, activityOpt.get());
    int actual = ((ProjectActivity) activityOpt.get()).getEndWeekNumber();
    assertEquals(expectedWeek, actual, "Incorrect end week number");
  }

  @Then("the project activity {string} has expected hours {double}")
  public void theProjectActivityHasExpectedHours(String title, double expected) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);
    assertInstanceOf(ProjectActivity.class, activityOpt.get());
    double actual = ((ProjectActivity) activityOpt.get()).getExpectedHours();
    assertEquals(expected, actual, 0.01, "Incorrect expected hours");
  }

  @When("the project activity {string} field {string} is changed to {string}")
  public void theProjectActivityFieldIsChangedTo(String title, String field, String valueStr) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);
    assertInstanceOf(ProjectActivity.class, activityOpt.get());

    Object value;
    try {
      if (field.equalsIgnoreCase("expectedhours")) {
        value = Double.parseDouble(valueStr);
      } else {
        value = Integer.parseInt(valueStr);
      }
    } catch (NumberFormatException e) {
      value = valueStr;
    }

    Map<String, Object> data = Map.of(field.toLowerCase(), value);
    Optional<String> error = services.getActivityService().editActivity(activityOpt.get(), data);
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @When("the project activity {string} field {string} is changed to {string} with null activity")
  public void theProjectActivityFieldIsChangedToWithNullActivity(String title, String field, String valueStr) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);

    Object value;
    try {
      if (field.equalsIgnoreCase("expectedhours")) {
        value = Double.parseDouble(valueStr);
      } else {
        value = Integer.parseInt(valueStr);
      }
    } catch (NumberFormatException e) {
      value = valueStr;
    }

    Map<String, Object> data = Map.of(field.toLowerCase(), value);
    Optional<String> error = services.getActivityService().editActivity(activityOpt.orElse(null), data);
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @When("the project activity {string} field {string} is changed to {int}")
  public void theProjectActivityFieldIsChangedTo(String title, String field, Integer value) {
    Optional<BaseActivity> activityOpt = services.getActivityService().get(title);
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title);
    assertInstanceOf(ProjectActivity.class, activityOpt.get());

    Map<String, Object> data = Map.of(field.toLowerCase(), value);
    Optional<String> error = services.getActivityService().editActivity(activityOpt.get(), data);

    error.ifPresent(errorMessage::setErrorMessage);
  }
}

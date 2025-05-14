package dtu.group5.cucumber;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import dtu.group5.backend.model.ProjectActivity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListUnregisteredActivitiesTodaySteps extends BaseStepDefinition {
    private List<ProjectActivity> unregisteredActivities;

  @When("coworker {string} views unregistered activities for today")
  public void coworkerViewsUnregisteredActivitiesForToday(String initials) {
    unregisteredActivities = services.getWorkedHoursService().listUnregisteredActivitiesToday(initials);
  }

  @Then("the system lists the following unregistered activities")
  public void theSystemListsTheFollowingUnregisteredActivities(List<List<String>> expectedTable) {
    List<List<String>> expectedRows = expectedTable.subList(1, expectedTable.size());

    List<List<String>> actualRows = unregisteredActivities.stream()
      .map(a -> List.of(
        String.valueOf(a.getProjectNumber()),
        a.getTitle()
      ))
      .toList();

    assertEquals(expectedRows, actualRows, "Unregistered activities do not match");
  }

}

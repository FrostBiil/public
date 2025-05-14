package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import dtu.group5.frontend.session.Session;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static dtu.group5.backend.util.DateUtil.formatDate;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListRegisteredHoursSteps extends BaseStepDefinition {

  private List<String[]> registeredTimeEntries = new ArrayList<>();

  @When("coworker {string} views their registered time today")
  public void coworkerViewsTheirRegisteredTimeToday(String initials) {
    Coworker coworker = Session.getInstance().getLoggedInUser();
    if (coworker == null || !coworker.getInitials().equals(initials)) {
      throw new IllegalStateException("Coworker is not logged in");
    }

    registeredTimeEntries = services.getCoworkerService().getRegisteredTimeEntriesToday(initials);
  }

  @When("coworker {string} views their registered time")
  public void coworkerViewsTheirRegisteredTime(String initials) {
    Coworker coworker = Session.getInstance().getLoggedInUser();
    if (coworker == null || !coworker.getInitials().equals(initials)) {
      throw new IllegalStateException("Coworker is not logged in");
    }

    registeredTimeEntries = services.getCoworkerService().getRegisteredTimeEntries(initials);
  }

  @Then("the system lists the following time entries")
  public void theSystemListsTheFollowingTimeEntries(DataTable expectedTable) {
    List<List<String>> expected = expectedTable.asLists(String.class);

    for (int i = 1; i < expected.size(); i++) {
      List<String> expectedRow = expected.get(i);
      boolean rowFound = false;

      for (String[] actualRow : registeredTimeEntries) {
        if (actualRow.length != expectedRow.size()) continue;

        boolean match = true;
        for (int j = 0; j < actualRow.length; j++) {
          String expectedValue = expectedRow.get(j);
          if ("today".equalsIgnoreCase(expectedValue)) {
            expectedValue = formatDate(new Date());
          }

          if (!expectedValue.equals(actualRow[j])) {
            match = false;
            break;
          }
        }

        if (match) {
          rowFound = true;
          break;
        }
      }

      assertTrue(rowFound, "Expected row not found: " + String.join(", ", expectedRow));
    }
  }
}

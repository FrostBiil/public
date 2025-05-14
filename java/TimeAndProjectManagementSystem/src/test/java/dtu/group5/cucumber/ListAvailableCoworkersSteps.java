package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListAvailableCoworkersSteps extends BaseStepDefinition {

    private List<Coworker> availableCoworkers;

    @When("{string} requests a list of available coworkers for project {int} activity {string}")
    public void requestsAListOfAvailableCoworkersForProjectActivity(String user, int projectNumber, String activityName) {

        Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
        assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

        Optional<ProjectActivity> activityOpt = services.getProjectService().getProjectActivities(projectOpt.get()).stream()
                .filter(a -> a.getTitle().equals(activityName))
                .findFirst();
        assertTrue(activityOpt.isPresent(), "Activity not found: " + activityName + " in project " + projectNumber);

        availableCoworkers = services.getActivityService().listAvailableCoworkers(activityOpt.get());
    }

    @Then("the system displays the following employees as available:")
    public void theSystemDisplaysTheFollowingEmployeesAsAvailable(DataTable dataTable) {
        List<String> expectedNames = dataTable.asLists().stream()
                .skip(1) // Skip the header row
                .map(List::getFirst)
                .toList();

        List<String> actualNames = availableCoworkers.stream()
                .map(Coworker::getName) // or getUsername(), depending on your model
                .toList();

        Set<String> expectedNameSet = new HashSet<>(expectedNames);
        Set<String> actualNameSet = new HashSet<>(actualNames);

        assertEquals(expectedNameSet, actualNameSet);
    }
}

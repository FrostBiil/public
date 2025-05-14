package dtu.group5.cucumber;

import dtu.group5.backend.model.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignProjectDatesSteps extends BaseStepDefinition {
    @When("the start date {string} is set for project {int}")
    public void setStartDateForProject(String startDateStr, int projectNumber) {
        try {
            Date startDate = parseDate(startDateStr);
            Project project = getProjectOrFail(projectNumber);
            Optional<String> error = ServiceHolder.getInstance().getProjectService()
              .editProject(project, Map.of("startdate", startDate));
            error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
        } catch (ParseException e) {
            ErrorMessageHolder.getInstance().setErrorMessage("Invalid date format. Use dd-mm-yyyy");
        }
    }

    @When("the end date {string} is set for project {int}")
    public void setEndDateForProject(String endDateStr, int projectNumber) {
        try {
            Date endDate = parseDate(endDateStr);
            Project project = getProjectOrFail(projectNumber);
            Optional<String> error = ServiceHolder.getInstance().getProjectService()
              .editProject(project, Map.of("enddate", endDate));
            error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
        } catch (ParseException e) {
            ErrorMessageHolder.getInstance().setErrorMessage("Invalid date format. Use dd-mm-yyyy");
        }
    }

    @Given("the project {int} has start date {string}")
    public void theProjectHasStartDate(int projectNumber, String startDateStr) throws Exception {
        Date startDate = parseDate(startDateStr);
        Project project = getProjectOrFail(projectNumber);
        Optional<String> error = ServiceHolder.getInstance().getProjectService()
          .editProject(project, Map.of("startdate", startDate));
        assertTrue(error.isEmpty(), "Failed to set initial start date");
    }

    @Then("the project {int} has end date {string}")
    public void theProjectHasEndDate(int projectNumber, String expectedDateStr) throws Exception {
        Date expectedDate = parseDate(expectedDateStr);
        Project project = getProjectOrFail(projectNumber);
        assertEquals(expectedDate, project.getEndDate(), "End date does not match");
    }

    private Project getProjectOrFail(int projectNumber) {
        return ServiceHolder.getInstance().getProjectService()
          .get(projectNumber)
          .orElseThrow(() -> new RuntimeException("Project " + projectNumber + " not found"));
    }

    private Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        return sdf.parse(dateStr);
    }
}

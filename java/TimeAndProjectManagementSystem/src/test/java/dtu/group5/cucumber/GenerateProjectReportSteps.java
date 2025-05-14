package dtu.group5.cucumber;

import dtu.group5.backend.model.Project;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenerateProjectReportSteps extends BaseStepDefinition {

    List<String[]> reportData;

    @When("the project leader requests a report for project {int}")
    public void the_project_leader_requests_a_report_for_project(Integer projectNumber) {
        Optional<Project> project = services.getProjectService().get(projectNumber);
        assertTrue(project.isPresent(), "Project not found");

        try {
            this.reportData = services.getProjectService().generateWorkloadReport(project.get());
        } catch (IllegalArgumentException e) {
            errorMessage.setErrorMessage(e.getMessage());
        }
    }


    @Then("the following activity report is returned")
    public void theFollowingActivityReportIsReturned(io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> expectedReportData = dataTable.asLists(String.class);

        // Check if every row in the expected report data is present in the actual report data
        for (List<String> expectedRow : expectedReportData) {
            boolean rowFound = false;
            for (String[] reportRow : reportData) {

                if (reportRow.length == expectedRow.size()) {
                    boolean match = true;

                    // Compare each column in the row first by length and then by content
                    for (int i = 0; i < reportRow.length; i++) {
                        if (!reportRow[i].equals(expectedRow.get(i))) {
                            match = false;
                            break;
                        }
                    }

                    // If all columns match, set rowFound to true
                    if (match) {
                        rowFound = true;
                        break;
                    }
                }
            }

            // If the row was not found, assert false
            assertTrue(rowFound, "Expected row not found in the report: " + String.join(", ", expectedRow));
        }
    }
}
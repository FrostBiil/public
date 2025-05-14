package dtu.group5.cucumber;

import dtu.group5.backend.model.Project;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.Optional;

public class DeleteProjectSteps extends BaseStepDefinition{

    @When("a project is deleted")
    public void aCoworkerIsDeleted(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().getFirst();

        int projectNumber = Integer.parseInt(row.get("Project Number"));

        Project project = services.getProjectService().get(projectNumber).orElse(null);

        Optional<String> error = ServiceHolder.getInstance().getProjectService().removeProject(project);
        error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    }
}

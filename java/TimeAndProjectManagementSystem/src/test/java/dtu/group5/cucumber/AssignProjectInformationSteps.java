package dtu.group5.cucumber;

import dtu.group5.backend.model.Project;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.Optional;

public class AssignProjectInformationSteps extends BaseStepDefinition {
    @When("the title of project {int} is set to {string}")
    public void setTitleOfProject(int projectNumber, String newTitle) {
        Optional<Project> projectOpt = ServiceHolder.getInstance().getProjectService().get(projectNumber);
        if (projectOpt.isEmpty()) {
            ErrorMessageHolder.getInstance().setErrorMessage("Project not found");
            return;
        }

        Optional<String> error = ServiceHolder.getInstance()
          .getProjectService()
          .editProject(projectOpt.get(), Map.of("title", newTitle));
        error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    }

    @When("the description of project {int} is set to {string}")
    public void setDescriptionOfProject(int projectNumber, String newDescription) {
        Optional<Project> projectOpt = ServiceHolder.getInstance().getProjectService().get(projectNumber);
        if (projectOpt.isEmpty()) {
            ErrorMessageHolder.getInstance().setErrorMessage("Project not found");
            return;
        }

        Optional<String> error = ServiceHolder.getInstance()
          .getProjectService()
          .editProject(projectOpt.get(), Map.of("description", newDescription));
        error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    }
}

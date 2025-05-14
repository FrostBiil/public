package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssignProjectLeaderSteps extends BaseStepDefinition {
    @When("coworker with initials {string} is assigned as project leader of project {int}")
    public void assignCoworkerAsProjectLeader(String initials, int projectNumber) {
        Project project = getProjectOrFail(projectNumber);
        Coworker coworker = getCoworkerOrFail(initials);

        Optional<String> error = services.getProjectService()
          .editProject(project, Map.of("project-leader", coworker.getInitials()));
        error.ifPresent(errorMessage::setErrorMessage);
    }

    @Given("coworker {string} is not added to project {int}")
    public void coworkerIsNotAddedToProject(String initials, int projectNumber) {
        Project project = getProjectOrFail(projectNumber);
        assertTrue(project.getCoworkers().stream().noneMatch(c -> c.getInitials().equals(initials)),
          "Coworker " + initials + " should not be part of the project");
    }

    @When("coworker {string} is assigned as project leader")
    public void coworkerIsAssignedAsProjectLeader(String initials) {
        Project project = services.getProjectService().getList().getFirst();
        Coworker coworker = services.getCoworkerService().get(initials).orElse(new Coworker(initials, "Dummy"));

        Optional<String> error = services.getProjectService()
          .editProject(project, Map.of("project-leader", coworker.getInitials()));
        error.ifPresent(errorMessage::setErrorMessage);
    }

    @When("coworker {string} is assigned as project leader for project number {int}")
    public void coworkerIsAssignedAsProjectLeaderForProjectNumber(String initials, int projectNumber) {
        Project project = services.getProjectService().getList().stream().filter(p -> p.getProjectNumber() == projectNumber).findFirst().orElse(null);
        assertNotNull(project, "Project not found with number: " + projectNumber);
        Coworker coworker = services.getCoworkerService().get(initials).orElse(new Coworker(initials, "Dummy"));

        project.setProjectLeader(coworker);

        assertEquals(initials, project.getProjectLeader().getInitials());
    }

    @When("project leader for project number {int} is removed")
    public void projectLeaderForProjectNumberIsRemoved(int projectNumber) {
        Project project = services.getProjectService().getList().stream().filter(p -> p.getProjectNumber() == projectNumber).findFirst().orElse(null);
        assertNotNull(project, "Project not found with number: " + projectNumber);

        project.setProjectLeader(null);
        assertNull(project.getProjectLeader(), "Project leader should be null");
    }

    @Given("coworker with initials {string} is currently project leader")
    public void coworkerIsCurrentlyProjectLeader(String initials) {
        Project project = services.getProjectService().getList().getFirst();
        Coworker leader = getCoworkerOrFail(initials);

        project.setProjectLeader(leader);
        assertEquals(initials, project.getProjectLeader().getInitials());
    }

    @When("coworker with initials {string} is assigned as new project leader of project {int}")
    public void coworkerWithInitialsIsAssignedAsNewProjectLeaderOfProject(String initials, int projectNumber) {
        Project project = getProjectOrFail(projectNumber);
        Coworker coworker = getCoworkerOrFail(initials);

        Optional<String> error = services.getProjectService()
          .editProject(project, Map.of("project-leader", coworker.getInitials()));
        error.ifPresent(errorMessage::setErrorMessage);
    }

    private Project getProjectOrFail(int projectNumber) {
        return services.getProjectService().get(projectNumber)
          .orElseThrow(() -> new AssertionError("Project not found: " + projectNumber));
    }

    private Coworker getCoworkerOrFail(String initials) {
        return services.getCoworkerService().get(initials)
          .orElseThrow(() -> new AssertionError("Coworker not found: " + initials));
    }
}


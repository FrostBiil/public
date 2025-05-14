package dtu.group5.cucumber;

import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectType;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.parseDate;

public class CreateProjectSteps extends BaseStepDefinition {

  @Given("there are no existing projects")
  public void thereAreNoExistingProjects() {
    repository.getProjectRepository().clear();
  }

  @When("a new project is created")
  public void createNewProject(DataTable dataTable) {
    Map<String, String> row = dataTable.asMaps().getFirst();

    Map<String, Object> fields = new HashMap<>();
    fields.put("title", row.get("Title"));
    fields.put("description", row.get("Description"));
    fields.put("startdate", parseDate(row.get("Start Date")));
    fields.put("enddate", parseDate(row.get("End Date")));
    fields.put("type", row.containsKey("Type") ? ProjectType.fromString(row.get("Type")) : ProjectType.INTERN);

    Optional<String> error = ServiceHolder.getInstance().getProjectService().create(fields);
    error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
  }


  @When("a new project with an empty title is created")
  public void createProjectWithEmptyTitle() {
    Map<String, Object> fields = new HashMap<>();
    fields.put("title", "");
    fields.put("description", "Description");
    fields.put("type", ProjectType.INTERN);

    Optional<String> error = ServiceHolder.getInstance().getProjectService().create(fields);
    error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
  }


  @When("a new project with project number {int} is created")
  public void createProjectWithExistingNumber(int projectNumber) {
    Map<String, Object> fields = new HashMap<>();
    fields.put("title", "Duplicate Project");
    fields.put("description", "Description");
    fields.put("type", ProjectType.INTERN);

    Project project = new Project(projectNumber, null);
    fields.forEach((key, value) ->
      ServiceHolder.getInstance().getProjectService().getCreators().stream()
        .filter(c -> c.supports(key))
        .findFirst()
        .ifPresent(c -> c.create(project, value))
    );

    Optional<String> error;
    if (ServiceHolder.getInstance().getProjectService().get(projectNumber).isPresent()) {
      error = Optional.of("Project number already exists");
    } else {
      error = ServiceHolder.getInstance().getProjectService().create(fields);
    }

    error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
  }


  @Given("project {int} already exists")
  public void projectAlreadyExists(int projectNumber) {
    Map<String, Object> fields = new HashMap<>();
    fields.put("title", "Existing Project");
    fields.put("description", "Existing Description");
    fields.put("type", ProjectType.INTERN);

    Project project = new Project(projectNumber, null);
    fields.forEach((key, value) ->
      ServiceHolder.getInstance().getProjectService().getCreators().stream()
        .filter(c -> c.supports(key))
        .findFirst()
        .ifPresent(c -> c.create(project, value))
    );

    repository.getProjectRepository().add(project);
  }


  @When("a new project is created with start date {string} and end date {string}")
  public void aNewProjectIsCreatedWithStartDateAndEndDate(String arg0, String arg1) {
    Map<String, Object> fields = new HashMap<>();
    fields.put("title", "Project");
    fields.put("description", "Description");
    fields.put("startdate", parseDate(arg0));
    fields.put("enddate", parseDate(arg1));
    fields.put("type", ProjectType.INTERN);

    Optional<String> error = ServiceHolder.getInstance().getProjectService().create(fields);
    error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
  }
}

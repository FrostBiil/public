package dtu.group5.cucumber;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.model.ProjectType;
import dtu.group5.frontend.session.Session;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dtu.group5.backend.service.AssignmentService.isCoworkerAssigned;
import static dtu.group5.backend.util.DateUtil.parseDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SystemSteps extends BaseStepDefinition {

  @Given("the project system contains the following coworkers")
  public void systemContainsCoworkers(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      String initials = row.get("Initials");
      String name = row.get("Name");

      Optional<String> responce = services.getCoworkerService().create(new Coworker(initials, name));

        if (responce.isPresent())
            fail(responce.get());
    }
  }

  @And("coworker with initials {string} is logged in to project system")
  public void loginCoworker(String initials) {
    Optional<Coworker> coworker = services.getCoworkerService().get(initials);
    assertTrue(coworker.isPresent(), "Coworker with initials " + initials + " not found");
    Session.getInstance().setLoggedInUser(coworker.get());

    assertEquals(coworker.get().getInitials(), Session.getInstance().getLoggedInUser().getInitials());
  }

  @And("coworker logout")
  public void logoutCoworker() {
    Session.getInstance().setLoggedInUser(null);
    assertNull(Session.getInstance().getLoggedInUser());
  }

  @And("coworker with initials {string} and {string} are added to project {int}")
  public void addTwoCoworkersToProject(String initials1, String initials2, int projectNumber) {
    Project project = services.getProjectService().get(projectNumber)
      .orElseThrow(() -> new RuntimeException("Project " + projectNumber + " not found"));
    Coworker coworker1 = services.getCoworkerService().get(initials1)
      .orElseThrow(() -> new RuntimeException("Coworker " + initials1 + " not found"));
    Coworker coworker2 = services.getCoworkerService().get(initials2)
      .orElseThrow(() -> new RuntimeException("Coworker " + initials2 + " not found"));

    services.getAssignmentService().assignCoworkerToProject(project, coworker1);
    services.getAssignmentService().assignCoworkerToProject(project, coworker2);

    assertTrue(isCoworkerAssigned(project, coworker1), "Coworker " + initials1 + " not assigned to project " + projectNumber);
    assertTrue(isCoworkerAssigned(project, coworker2), "Coworker " + initials2 + " not assigned to project " + projectNumber);
  }

  @And("the project system contains the following projects")
  public void systemContainsProjects(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      Map<String, Object> fields = new HashMap<>();
      fields.put("title", row.get("Title"));
      fields.put("description", row.get("Description"));
      fields.put("startdate", parseDate(row.get("Start Date")));
      fields.put("enddate", parseDate(row.get("End Date")));
      fields.put("type", row.containsKey("Type") ? ProjectType.fromString(row.get("Type")) : ProjectType.INTERN);

      Optional<String> response = services.getProjectService().create(fields);
      response.ifPresent(Assertions::fail);
    }
  }

  @And("the project system has the following projects")
  public void systemHasProjects(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
        int projectNumber = Integer.parseInt(row.get("Project Number"));
        String title = row.get("Title");
        String description = row.get("Description");
        ProjectType type = row.containsKey("Type") ? ProjectType.fromString(row.get("Type")) : ProjectType.INTERN;

        boolean found = services.getProjectService().getList().stream().anyMatch(p -> p.getProjectNumber() == projectNumber && p.getTitle().equals(title) && p.getDescription().equals(description) && p.getType() == type);

        assertTrue(found, "Project with number " + projectNumber + " not found");
    }
  }

  @And("the project system contains the following project activities")
  public void systemContainsActivities(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      int projectNumber = Integer.parseInt(row.get("Project Number"));
      ProjectActivity activity = new ProjectActivity(
              projectNumber,
              row.get("Activity Title"),
              Double.parseDouble(row.get("Expected Hours")),
              Boolean.parseBoolean(row.get("Finished")),
              row.get("Description"),
              new HashSet<>(),
              Integer.parseInt(row.get("Start Year")),
              Integer.parseInt(row.get("Start Week Number")),
              Integer.parseInt(row.get("End Year")),
              Integer.parseInt(row.get("End Week Number")),
              new HashMap<>()
      );
      Optional<String> responce = services.getActivityService().create(activity);

      responce.ifPresent(Assertions::fail);
    }
  }

  @And("the project system contains the following fixed activities")
  public void systemContainsFixedActivities(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
        String coworkerInitials = row.get("Coworker Initials");
        String title = row.get("Activity Title");
        boolean finished = Boolean.parseBoolean(row.get("Finished"));
        String description = row.get("Description");
        Date startDate = parseDate(row.get("Start Date"));
        Date endDate = parseDate(row.get("End Date"));
        Optional<Coworker> coworkerOpt = services.getCoworkerService().get(coworkerInitials);

        assertTrue(coworkerOpt.isPresent(), "Coworker with initials " + coworkerInitials + " not found");


        Coworker coworker = coworkerOpt.get();

        FixedActivity fixedActivity = new FixedActivity(title, finished, description, startDate, endDate, coworker);

        Optional<String> responce = services.getActivityService().create(fixedActivity);

        responce.ifPresent(Assertions::fail);

    }
  }

  @And("the project system tries to create the following fixed activities")
  public void systemTriesToCreateFixedActivities(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      String coworkerInitials = row.get("Coworker Initials");
      String title = row.get("Activity Title");
      boolean finished = Boolean.parseBoolean(row.get("Finished"));
      String description = row.get("Description");
      Date startDate = parseDate(row.get("Start Date"));
      Date endDate = parseDate(row.get("End Date"));
      Optional<Coworker> coworkerOpt = services.getCoworkerService().get(coworkerInitials);

      assertTrue(coworkerOpt.isPresent(), "Coworker with initials " + coworkerInitials + " not found");


      Coworker coworker = coworkerOpt.get();

      FixedActivity fixedActivity = new FixedActivity(title, finished, description, startDate, endDate, coworker);

      Optional<String> responce = services.getActivityService().create(fixedActivity);

      responce.ifPresent(errorMessage::setErrorMessage);

    }
  }

  @Given("the project {int} exists")
  public void projectExists(int projectNumber) {
    assertTrue(services.getProjectService().get(projectNumber).isPresent(),
      "Project " + projectNumber + " does not exist");
  }

  @Then("the project {int} has title {string}")
  public void projectHasTitle(int projectNumber, String expectedTitle) {
    Project project = services.getProjectService().get(projectNumber)
      .orElseThrow(() -> new RuntimeException("Project not found: " + projectNumber));
    assertEquals(expectedTitle, project.getTitle());
  }

  @Then("the project {int} has description {string}")
  public void projectHasDescription(int projectNumber, String expectedDescription) {
    Project project = services.getProjectService().get(projectNumber)
      .orElseThrow(() -> new RuntimeException("Project not found: " + projectNumber));
    assertEquals(expectedDescription, project.getDescription());
  }

  @Then("the project {int} has type {string}")
  public void projectHasType(int projectNumber, String expectedType) {
    Project project = services.getProjectService().get(projectNumber)
            .orElseThrow(() -> new RuntimeException("Project not found: " + projectNumber));
    assertEquals(expectedType, project.getType().name());
  }

  @Then("the project {int} has status {string}")
  public void projectHasStatus(int projectNumber, String expectedStatus) {
    Project project = services.getProjectService().get(projectNumber)
            .orElseThrow(() -> new RuntimeException("Project not found: " + projectNumber));
    assertEquals(expectedStatus, project.getStatus().name());
  }

  @Then("the project leader of project {int} is {string}")
  public void projectLeaderIs(int projectNumber, String expectedInitials) {
    Project project = services.getProjectService().get(projectNumber)
      .orElseThrow(() -> new RuntimeException("Project not found: " + projectNumber));
    assertNotNull(project.getProjectLeader(), "No project leader assigned");
    assertEquals(expectedInitials, project.getProjectLeader().getInitials());
  }

  @Then("the project {int} has the following coworkers")
  public void projectHasCoworkers(int projectNumber, DataTable dataTable) {
    Project project = services.getProjectService().get(projectNumber)
      .orElseThrow(() -> new RuntimeException("Project " + projectNumber + " not found"));
    List<Map<String, String>> expected = dataTable.asMaps();

    assertEquals(expected.size(), project.getCoworkers().size(), "Mismatch in number of coworkers");

    for (Map<String, String> row : expected) {
      String initials = row.get("Initials");
      String name = row.get("Name");

      Coworker coworker = project.getCoworkers().stream()
        .filter(c -> c.getInitials().equals(initials))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Coworker " + initials + " not found"));

      assertEquals(name, coworker.getName(), "Coworker name mismatch for " + initials);
    }
  }

  @Then("the activity {string} has the following coworkers")
  public void activityHasCoworkers(String title, DataTable dataTable) {
    BaseActivity activity = services.getActivityService().findActivityByTitle(title)
      .orElseThrow(() -> new RuntimeException("Activity " + title + " not found"));
    List<Map<String, String>> expected = dataTable.asMaps();

    assertEquals(expected.size(), activity.getAssignedCoworkers().size(), "Mismatch in assigned coworkers");

    for (Map<String, String> row : expected) {
      String initials = row.get("Initials");
      String name = row.get("Name");

      Coworker coworker = activity.getAssignedCoworkers().stream()
        .filter(c -> c.getInitials().equals(initials))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Coworker " + initials + " not found in activity"));

      assertEquals(name, coworker.getName(), "Coworker name mismatch for " + initials);
    }
  }

  @Then("the project {int} activity {string} is finished")
  public void theProjectActivityIsFinished(int projectNumber, String title) {
    Optional<Project> projectOpt = services.getProjectService().get(projectNumber);
    assertTrue(projectOpt.isPresent(), "Project not found: " + projectNumber);

    Optional<ProjectActivity> activityOpt = services.getProjectService().getProjectActivities(projectOpt.get()).stream()
      .filter(a -> a.getTitle().equals(title))
      .findFirst();
    assertTrue(activityOpt.isPresent(), "Activity not found: " + title + " in project " + projectNumber);

    assertTrue(activityOpt.get().isFinished(), "Activity is not marked as finished");
  }

  @Then("the project system has the following fixed activities")
  public void systemHasFixedActivities(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      String title = row.get("Title");
      boolean finished = Boolean.parseBoolean(row.get("Finished"));
      String description = row.get("Description");
      Date startDate = parseDate(row.get("StartDate"));
      Date endDate = parseDate(row.get("EndDate"));
      Optional<Coworker> coworker = repository.getCoworkerRepository().get(row.get("Initials"));
      coworker.ifPresent(value -> repository.getActivityRepository().getList().stream()
              .filter(a -> a instanceof FixedActivity && a.getTitle().equals(title))
              .forEach(a -> {
                FixedActivity fa = (FixedActivity) a;
                assertEquals(finished, fa.isFinished(), "Activity finished status mismatch");
                assertEquals(description, fa.getDescription(), "Activity description mismatch");
                assertEquals(startDate, fa.getStartDate(), "Activity start date mismatch");
                assertEquals(endDate, fa.getEndDate(), "Activity end date mismatch");
                assertTrue(fa.getAssignedCoworkers().contains(value), "Coworker not assigned to activity");
              }));
    }
  }

  @Then("the project system has the following project activities")
  public void systemHasProjectActivities(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
        int projectNumber = Integer.parseInt(row.get("Project Number"));
      String title = row.get("Title");
      boolean finished = Boolean.parseBoolean(row.get("Finished"));
      String description = row.get("Description");


      repository.getActivityRepository().getList().stream()
          .filter(a -> a instanceof ProjectActivity && a.getTitle().equals(title))
          .forEach(a -> {
            ProjectActivity fa = (ProjectActivity) a;
            assertEquals(projectNumber, fa.getProjectNumber(), "Project number mismatch for activity " + fa.getTitle());
            assertEquals(finished, fa.isFinished(), "Activity finished status mismatch");
            assertEquals(description, fa.getDescription(), "Activity description mismatch");
          });
    }
  }

  @Then("the project system has the following coworkers")
  public void systemHasCoworkers(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      String initials = row.get("Initials");
      String name = row.get("Name");

      Optional<Coworker> coworker = services.getCoworkerService().get(initials);
      assertTrue(coworker.isPresent(), "Coworker with initials " + initials + " not found");
      assertEquals(name, coworker.get().getName(), "Coworker name mismatch for " + initials);
    }
  }

  @Then("the project system has the following activities")
  public void systemHasActivities(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> row : rows) {
      int projectNumber = Integer.parseInt(row.get("Project Number"));
      services.getActivityService().getList().stream()
              .filter(a -> a instanceof ProjectActivity)
              .map(a -> (ProjectActivity) a)
              .forEach(a -> {
                assertEquals(a.getProjectNumber(), projectNumber, "Project number mismatch for activity " + a.getTitle());
                assertEquals(row.get("Activity Title"), a.getTitle(), "Activity title mismatch for activity " + a.getTitle());
                assertEquals(row.get("Description"), a.getDescription(), "Project description mismatch for activity " + a.getTitle());
                assertEquals(Integer.parseInt(row.get("Start Year")), a.getStartYear(), "Project start year mismatch for activity " + a.getTitle());
                assertEquals(Integer.parseInt(row.get("Start Week Number")), a.getStartWeekNumber(), "Project start week number mismatch for activity " + a.getTitle());
                assertEquals(Integer.parseInt(row.get("End Year")), a.getEndYear(), "Project end year mismatch for activity " + a.getTitle());
                assertEquals(Integer.parseInt(row.get("End Week Number")), a.getEndWeekNumber(), "Project end week number mismatch for activity " + a.getTitle());
                assertEquals(Double.parseDouble(row.get("Expected Hours")), a.getExpectedHours(), "Project expected hours mismatch for activity " + a.getTitle());
                assertEquals(Boolean.parseBoolean(row.get("Finished")), a.isFinished(), "Project finished status mismatch for activity " + a.getTitle());
              });
    }
  }

  @Then("the error message {string} is given")
  public void errorMessageIsGiven(String expectedErrorMessage) {
    assertEquals(expectedErrorMessage, errorMessage.getErrorMessage(), "Error message mismatch");
  }

  @And("the project system contains no projects")
  public void theProjectSystemContainsNoProjects() {
    repository.clear();
    assertTrue(services.getProjectService().getList().isEmpty(), "Project system is not empty");
  }
}
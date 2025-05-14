package dtu.group5.junit5.service;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.FixedActivity;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.ProjectRepository;
import dtu.group5.backend.service.ActivityService;
import dtu.group5.backend.service.AssignmentService;
import dtu.group5.backend.service.CoworkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivityServiceTest {

    private CoworkerService coworkerService;
    private AssignmentService assignmentService;
    private ActivityService activityService;

    private Project testProject;

    private Coworker coworker;

    private ProjectActivity testProjectActivityToAssignTo;
    private ProjectActivity testProjectActivityOverlap;
    private FixedActivity testFixedActivityToAssignTo1;
    private FixedActivity testFixedActivityToAssignTo2;

    @BeforeEach
    void setUp() {
        coworkerService = new CoworkerService();
        assignmentService = new AssignmentService(coworkerService);
        activityService = new ActivityService(assignmentService, coworkerService);

        testProject = new Project(25001, "Test Project");
        ProjectRepository.getInstance().add(testProject);


        coworker = new Coworker("alp", "Alpha");
        coworkerService.create(coworker);

        // Initialize activities
        this.testProjectActivityToAssignTo = new ProjectActivity(25001, "Test Activity", 5, false, "Test Description", new HashSet<>(), 25, 1, 25, 3, new HashMap<>());
        this.testProjectActivityOverlap = new ProjectActivity(25001, "Overlap Project Activity", 5, false, "Overlap Description", new HashSet<>(), 25, 1, 25, 4, new HashMap<>());


        // Get Dates
        Date startDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date endDate = calendar.getTime();

        this.testFixedActivityToAssignTo1 = new FixedActivity("Fixed Activity 1", false, "Fixed Description", new HashSet<>(),  startDate, endDate);
        this.testFixedActivityToAssignTo2 = new FixedActivity("Fixed Activity 2", false, "Fixed Description", new HashSet<>(),  startDate, endDate);
        this.testFixedActivityToAssignTo1.addAssignedCoworker(coworker);

        this.activityService.create(testProjectActivityToAssignTo);
        this.activityService.create(testProjectActivityOverlap);
        this.activityService.create(testFixedActivityToAssignTo1);
    }

    @Test
    void isCoworkerAvailableSuccessfullyProjectActivity() {
        boolean responce = activityService.isCoworkerAvailable(coworker, testProjectActivityToAssignTo);
        assertTrue(responce, "Expected coworker to be available for the activity");
    }

    @Test
    void isCoworkerAvailableSuccessfullyFixedActivity() {
        ActivityRepository.getInstance().clear();
        boolean responce = activityService.isCoworkerAvailable(coworker, testFixedActivityToAssignTo2);
        assertTrue(responce, "Expected coworker to be available for the activity");
    }

    @Test
    void isCoworkerAvailableNullActivity() {
        boolean responce = activityService.isCoworkerAvailable(coworker, null);
        assertFalse(responce, "Expected coworker to be available for the activity");
    }

    @Test
    void isCoworkerAvailableProjectActivityAlreadyAssigned() {
        assignmentService.assignCoworkerToActivity(testProjectActivityToAssignTo, coworker, false);
        boolean responce = activityService.isCoworkerAvailable(coworker, testProjectActivityToAssignTo);
        assertFalse(responce, "Expected coworker to be unavailable for the activity");
    }

    @Test
    void isCoworkerAvailableFixedActivityAlreadyAssigned() {
        assignmentService.assignCoworkerToActivity(testFixedActivityToAssignTo1, coworker, false);
        boolean responce = activityService.isCoworkerAvailable(coworker, testFixedActivityToAssignTo1);
        assertFalse(responce, "Expected coworker to be unavailable for the activity");
    }

    @Test
    void isCoworkerAvailableOverlap() {
        assignmentService.assignCoworkerToActivity(testFixedActivityToAssignTo2, coworker, false);
        boolean responce = activityService.isCoworkerAvailable(coworker, testFixedActivityToAssignTo1);
        assertFalse(responce, "Expected coworker to be unavailable for the activity");
    }
}
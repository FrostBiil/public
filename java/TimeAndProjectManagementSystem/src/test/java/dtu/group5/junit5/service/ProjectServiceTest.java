package dtu.group5.junit5.service;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.repository.ActivityRepository;
import dtu.group5.backend.repository.ProjectRepository;
import dtu.group5.backend.service.ProjectService;
import dtu.group5.frontend.session.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectServiceTest {

    private ProjectService projectService;
    private Project testProject;
    private Coworker assignedCoworker;
    private Coworker unassignedCoworker;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService();

        testProject = new Project(25001, null);
        assignedCoworker = new Coworker("abc", "Alice");
        unassignedCoworker = new Coworker("xyz", "Bob");

        testProject.addCoworker(assignedCoworker);
        ProjectRepository.getInstance().add(testProject);
        testProject.setProjectLeader(assignedCoworker);

        // Login
        Session.getInstance().setLoggedInUser(assignedCoworker);
    }

    @Test
    void removeCoworkerSuccessfully() {
        Optional<String> result = projectService.removeCoworkerFromProject(testProject, assignedCoworker);
        assertTrue(result.isEmpty(), "Expected coworker to be removed successfully");
        assertFalse(testProject.getCoworkers().contains(assignedCoworker), "Coworker should no longer be in the project");
    }

    @Test
    void removeCoworkerFromNullProject() {
        Optional<String> result = projectService.removeCoworkerFromProject(null, assignedCoworker);
        assertTrue(result.isPresent());
        assertEquals("Project cannot be null", result.get());
    }

    @Test
    void removeNullCoworkerFromProject() {
        Optional<String> result = projectService.removeCoworkerFromProject(testProject, null);
        assertTrue(result.isPresent());
        assertEquals("Coworker cannot be null", result.get());
    }

    @Test
    void removeUnassignedCoworker() {
        Optional<String> result = projectService.removeCoworkerFromProject(testProject, unassignedCoworker);
        assertTrue(result.isPresent());
        assertEquals("Coworker is not assigned to the project", result.get());
    }


    @Test
    void removeProjectSuccessfully() {
        // Ensure the projects activities are cleared from the repository
        ActivityRepository.getInstance().clear();

        Optional<String> result = projectService.removeProject(testProject);
        assertTrue(result.isEmpty(), "Expected project to be removed successfully");
        assertFalse(ProjectRepository.getInstance().getList().contains(testProject), "Project should no longer exist");
    }

    @Test
    void removeProjectWithoutBeingLogin() {
        // Log out the user
        Session.getInstance().setLoggedInUser(null);

        Optional<String> result = projectService.removeProject(testProject);
        assertTrue(result.isPresent());
        assertEquals("Please login", result.get());
    }

    @Test
    void removeNullProject() {
        Optional<String> result = projectService.removeProject(null);
        assertTrue(result.isPresent());
        assertEquals("Project cannot be null", result.get());
    }

    @Test
    void removeProjectWithProjectLeaderNotLogin() {
        // New Login
        Session.getInstance().setLoggedInUser(unassignedCoworker);

        // Attempt to remove the project with a different logged-in user
        Optional<String> result = projectService.removeProject(testProject);
        assertTrue(result.isPresent());
        assertEquals("Only the project leader can delete a project", result.get());
    }

    @Test
    void removeProjectWithActivity() {
        ProjectActivity testActivity = new ProjectActivity(25001, "Test Activity", 5, false, "Test Description", new HashSet<>(), 1, 25, 2, 25, new HashMap<>());
        ActivityRepository.getInstance().add(testActivity);

        Optional<String> result = projectService.removeProject(testProject);
        assertTrue(result.isPresent());
        assertEquals("Project has activities assigned to it, please remove them first", result.get());
    }
}
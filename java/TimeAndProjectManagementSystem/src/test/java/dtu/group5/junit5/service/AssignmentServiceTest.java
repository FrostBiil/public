package dtu.group5.junit5.service;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.Project;
import dtu.group5.backend.repository.ProjectRepository;
import dtu.group5.backend.service.AssignmentService;
import dtu.group5.backend.service.CoworkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssignmentServiceTest {

    private CoworkerService coworkerService;
    private AssignmentService assignmentService;
    private Project testProject;
    private Coworker assignedCoworker;
    private Coworker unassignedCoworker;

    @BeforeEach
    void setUp() {
        this.coworkerService = new CoworkerService();
        this.assignmentService = new AssignmentService(coworkerService);

        testProject = new Project(25001, null);

        assignedCoworker = new Coworker("abc", "Alice");
        unassignedCoworker = new Coworker("xyz", "Bob");

        testProject.addCoworker(assignedCoworker);
        ProjectRepository.getInstance().add(testProject);
    }

    @Test
    void assignCoworkerToProjectSuccessfully() {
        Optional<String> result = assignmentService.assignCoworkerToProject(testProject, unassignedCoworker);
        assertTrue(result.isEmpty(), "Expected coworker to be assigned successfully");
        assertTrue(testProject.getCoworkers().contains(assignedCoworker), "Coworker should be in the project");
    }

    @Test
    void assignCoworkerToNullProject() {
        Optional<String> result = assignmentService.assignCoworkerToProject(null, unassignedCoworker);
        assertTrue(result.isPresent());
        assertEquals("Project cannot be null", result.get());
    }

    @Test
    void assignNullCoworkerToProject() {
        Optional<String> result = assignmentService.assignCoworkerToProject(testProject, null);
        assertTrue(result.isPresent());
        assertEquals("Coworker cannot be null", result.get());
    }

    @Test
    void assignAlreadyAssignedCoworkerToProject() {
        Optional<String> result = assignmentService.assignCoworkerToProject(testProject, assignedCoworker);
        assertTrue(result.isPresent());
        assertEquals("Coworker is already part of the project", result.get());
    }
}
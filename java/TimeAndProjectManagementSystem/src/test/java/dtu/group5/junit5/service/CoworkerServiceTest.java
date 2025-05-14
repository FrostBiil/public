package dtu.group5.junit5.service;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.repository.CoworkerRepository;
import dtu.group5.backend.service.CoworkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoworkerServiceTest {

    private CoworkerService coworkerService;

    @BeforeEach
    void setUp() {
        this.coworkerService = new CoworkerService();

        coworkerService.create("cre", "Created Coworker");
    }

    @Test
    void createSuccessfully() {
        CoworkerRepository.getInstance().clear();
        String initials = "alp";
        String name = "Alpha";

        Optional<String> result = coworkerService.create(initials, name);
        assertTrue(result.isEmpty(), "Expected coworker to be created successfully");

        Optional<Coworker> coworker = coworkerService.get(initials);
        assertTrue(coworker.isPresent(), "Expected coworker to be present");
        assertEquals(initials, coworker.get().getInitials(), "Expected coworker's initials to match");
        assertEquals(name, coworker.get().getName(), "Expected coworker's name to match");
    }

    @Test
    void createWithNullInitials() {
        String initials = null;
        String name = "Alpha";

        Optional<String> result = coworkerService.create(initials, name);
        assertTrue(result.isPresent(), "Expected error when creating coworker with null initials");
        assertEquals("Initials cannot be empty", result.get());
    }


    @Test
    void createWithEmptyInitials() {
        String initials = "";
        String name = "Alpha";

        Optional<String> result = coworkerService.create(initials, name);
        assertTrue(result.isPresent(), "Expected error when creating coworker with empty initials");
        assertEquals("Initials cannot be empty", result.get());
    }

    @Test
    void createWithLongInitials() {
        String initials = "longinitials";
        String name = "Alpha";

        Optional<String> result = coworkerService.create(initials, name);
        assertTrue(result.isPresent(), "Expected error when creating coworker with long initials");
        assertEquals("Initials must be at most 4 characters", result.get());
    }


    @Test
    void createWithNullName() {
        String initials = "alp";
        String name = null;

        Optional<String> result = coworkerService.create(initials, name);
        assertTrue(result.isPresent(), "Expected error when creating coworker with null name");
        assertEquals("Name cannot be empty", result.get());
    }

    @Test
    void createWithEmptyName() {
        String initials = "alp";
        String name = "";

        Optional<String> result = coworkerService.create(initials, name);
        assertTrue(result.isPresent(), "Expected error when creating coworker with empty name");
        assertEquals("Name cannot be empty", result.get());
    }

    @Test
    void createWithDuplicateInitials() {
        String initials = "cre";
        String name = "Alpha";

        Optional<String> result = coworkerService.create(initials, name);
        assertTrue(result.isPresent(), "Expected error when creating coworker with duplicate initials");
        assertEquals("Initials already in use", result.get());
    }
}
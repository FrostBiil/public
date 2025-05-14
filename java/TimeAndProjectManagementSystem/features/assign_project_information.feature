Feature: Assign project information
  Description: Assign a title and description to an existing project.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    And the project system contains the following projects
      | Title      | Description      | Start Date | End Date   | Type |
      | Test Title | Test Description | 01-05-2025 | 14-05-2025  | CLIENT |
    And coworker with initials "alph" is logged in to project system
    And coworker with initials "alph" is added to project 25001
    And coworker with initials "alph" is assigned as project leader of project 25001

  Scenario: Assign a title to a project
    Given the project 25001 exists
    When the title of project 25001 is set to "Test Project"
    Then the project 25001 has title "Test Project"

  Scenario: Assign a description to a project
    Given the project 25001 exists
    When the description of project 25001 is set to "This is a test project description"
    Then the project 25001 has description "This is a test project description"

  Scenario: Attempt to assign empty title to project
    Given the project 25001 exists
    When the title of project 25001 is set to ""
    Then the error message "Project title cannot be empty" is given

  Scenario: Attempt to assign empty description to project
    Given the project 25001 exists
    When the description of project 25001 is set to ""
    Then the error message "Project description cannot be empty" is given

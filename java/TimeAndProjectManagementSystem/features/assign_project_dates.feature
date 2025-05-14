Feature: Assign project dates
  Description: Assign a start date and an end date to an existing project.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025  | CLIENT |
    And coworker with initials "alph" is logged in to project system
    And coworker with initials "alph" is added to project 25001
    And coworker with initials "alph" is assigned as project leader of project 25001

  Scenario: Assign a start date to a project
    Given the project 25001 exists
    When the start date "06-04-2025" is set for project 25001
    Then the project 25001 has start date "06-04-2025"

  Scenario: Assign an end date to a project
    Given the project 25001 exists
    When the end date "07-05-2025" is set for project 25001
    Then the project 25001 has end date "07-05-2025"

  Scenario: Attempt to assign an invalid start date format
    Given the project 25001 exists
    When the start date "2025/04/06" is set for project 25001
    Then the error message "Invalid date format. Use dd-mm-yyyy" is given

  Scenario: Attempt to assign an end date before start date
    Given the project 25001 has start date "06-04-2025"
    When the end date "05-04-2025" is set for project 25001
    Then the error message "End date cannot be before start date" is given

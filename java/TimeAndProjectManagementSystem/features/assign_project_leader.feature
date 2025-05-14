Feature: Assign project leader
  Description: Assign a coworker as project leader for an existing project.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025  | CLIENT |
    And coworker with initials "alph" is added to project 25001
    And coworker with initials "alph" is logged in to project system

  Scenario: Assign a coworker as project leader
    Given the project 25001 exists
    When coworker with initials "alph" is assigned as project leader of project 25001
    Then the project leader of project 25001 is "alph"

  Scenario: Reassign project leader
    Given the project 25001 exists
    And the project system contains the following coworkers
      | Initials | Name |
      | beta     | Beta |
    And coworker with initials "alph" is currently project leader
    And coworker with initials "beta" is added to project 25001
    When coworker with initials "beta" is assigned as new project leader of project 25001
    Then the project leader of project 25001 is "beta"

  Scenario: Attempt to assign a coworker not in the project as project leader
    Given coworker "beta" is not added to project 25001
    When coworker "beta" is assigned as project leader
    Then the error message "Coworker must be part of the project to be project leader" is given

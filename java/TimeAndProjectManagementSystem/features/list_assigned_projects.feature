Feature: View personal project overview
  Description: A coworker can view their assigned uncompleted projects.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | beta     | Beta  |
    And the project system contains the following projects
      | Title          | Description         | Start Date | End Date   |
      | Test Project   | Initial description | 01-05-2025 | 30-06-2025 |
      | Client Project | Client description  | 01-07-2025 | 30-08-2025 |
    And coworker with initials "beta" is assigned to projects
      | Project Number |
      | 25001          |
      | 25002          |
    And coworker with initials "beta" is logged in to project system

  Scenario: View list of uncompleted projects
    When coworker "beta" lists their projects
    Then the following projects are displayed
      | Project Number | Title          |
      | 25001          | Test Project   |
      | 25002          | Client Project |

  Scenario: Attempt to view projects as a non-existing coworker
    When coworker "unknown" lists their projects
    Then the error message "Coworker not found" is given

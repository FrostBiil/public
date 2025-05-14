Feature: View personal activity overview
  Description: A coworker can view their uncompleted activities and assigned work hours.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name |
      | beta     | Beta |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type  |
      | Test Project | Initial description | 01-01-2025 | 31-12-2025 | CLIENT|
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20.0           | false    | Test Description | 25         | 1                 | 25       | 2               |
      | 25001          | Test title 2   | 20.0           | false    | Test Description | 25         | 3                 | 25       | 4               |
    And coworker with initials "beta" is assigned to projects
      | Project Number |
      | 25001          |
    And coworker with initials "beta" is assigned to activities
      | Project Number | Activity Title |
      | 25001          | Test title     |
      | 25001          | Test title 2   |
    And coworker with initials "beta" is logged in to project system

  Scenario: View list of uncompleted activities
    When coworker "beta" checks their activities
    Then the following activities are displayed
      | Activity Title | Assigned Hours |
      | Test title     | 0.0            |
      | Test title 2   | 0.0            |

  Scenario: Attempt to view activities as a non-existing coworker
    When coworker "unknown" checks their activities
    Then the error message "Coworker not found" is given

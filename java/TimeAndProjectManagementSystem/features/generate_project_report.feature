Feature: Generate project report
  Description: Project leader can generate a report listing activities and work progress.
  Actor: Project Leader

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta  |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type  |
      | Test Project | Initial description | 01-01-2025 | 31-12-2025 | CLIENT|
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours  | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20              | false    | Test Description | 25         | 1                 | 25       | 2               |
      | 25001          | Test title 2   | 20              | false    | Test Description | 25         | 3                 | 25       | 4               |
    And coworker with initials "alph" is assigned to projects
      | Project Number |
      | 25001 |
    And coworker with initials "beta" is assigned to projects
      | Project Number |
      | 25001 |
    And coworker with initials "alph" is assigned to activities
      | Project Number | Activity Title |
      | 25001          | Test title     |
      | 25001          | Test title 2   |
    And coworker with initials "beta" is assigned to activities
      | Project Number | Activity Title |
      | 25001          | Test title     |
      | 25001          | Test title 2   |

    And coworker with initials "alph" is logged in to project system

  Scenario: Generate a project work report
    When the project leader requests a report for project 25001
    Then the following activity report is returned
      | Title        | Expected Hours | Worked Hours | Completed (%) | Expected Weeks | Finished | Assigned Coworkers |
      | Test title   | 20.0           | 0.0          | 0.0%          | 2              | No       | 2                  |
      | Test title 2 | 20.0           | 0.0          | 0.0%          | 2              | No       | 2                  |

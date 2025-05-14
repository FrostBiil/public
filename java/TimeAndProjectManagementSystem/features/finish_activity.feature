Feature: Finish an activity
  Description: Mark an activity as finished.
  Actor: Project Leader

  Background:
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type   |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Type   | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | INTERN | false    | Test Description | 25         | 1                 | 25       | 2               |

  Scenario: Successfully finish an activity
    When the project 25001 activity "Test title" is marked as finished
    Then the project 25001 activity "Test title" is finished

Feature: Edit Worked Time
  Description: Edit the worked time of a coworker for a specific project activity.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name |
      | beta     | Beta |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025  | CLIENT |
    And coworker with initials "beta" is added to project 25001
    And coworker with initials "beta" is assigned to activity "Test title" in project 25001
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | false    | Test Description | 25         | 1                 | 25       | 2               |
    And coworker "beta" registers work hours
      | Project Number | Activity Title | Date  | Hours |
      | 25001          | Test title     | today | 8     |

  Scenario:Edit worked time successfully
    When coworker "beta" edits 2 hours of work in project 25001 activity "Test title"
    Then the activity "Test title" has 2 hours registered hours from coworker "beta"

  Scenario: Attempt to edit negative hours
    When coworker "beta" edits -2 hours of work in project 25001 activity "Test title"
    Then the error message "Worked hours must be positive" is given

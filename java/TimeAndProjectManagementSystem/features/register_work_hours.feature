Feature: Register work hours
  Description: A coworker can register worked hours on an assigned activity.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | beta     | Beta  |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type   |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | INTERN |
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Type   | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | INTERN | false    | Test Description | 25         | 1                 | 25       | 2               |
    And coworker with initials "beta" is added to project 25001
    And coworker with initials "beta" is assigned to activity "Test title" in project 25001
    And coworker with initials "beta" is logged in to project system

  Scenario: Register worked hours successfully
    When coworker "beta" registers work hours
      | Project Number | Activity Title | Date  | Hours |
      | 25001          | Test title     | today | 8     |
    Then the activity "Test title" has 8 hours registered hours from coworker "beta"

  Scenario: Attempt to register hours on a completed activity
    Given the project 25001 activity "Test title" is marked as finished
    When coworker "beta" registers work hours
      | Project Number | Activity Title | Date  | Hours |
      | 25001          | Test title     | today | 8     |
    Then the error message "Cannot register time on a completed activity" is given

  Scenario: Attempt to register negative hours
    When coworker "beta" registers work hours
      | Project Number | Activity Title | Date  | Hours |
      | 25001          | Test title     | today | -8    |
    Then the error message "Worked time must be positive" is given

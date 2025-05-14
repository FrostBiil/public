Feature: Register hours on non-assigned activity
  Description: Allow coworkers to help on activities they are not assigned to.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | beta     | Beta  |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Type   | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | INTERN | false    | Test Description | 25         | 1                 | 25       | 2               |
    And coworker with initials "beta" is logged in to project system

  Scenario: Coworker registers hours on an activity they are not assigned to
    When coworker "beta" registers work hours
      | Project Number | Activity Title | Date  | Hours |
      | 25001          | Test title     | today | 3     |
    Then the activity "Test title" has 3 hours registered hours from coworker "beta"

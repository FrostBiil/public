Feature: Delete work hours
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
    And coworker "beta" registers work hours
      | Initials | Project Number | Activity Title | Hours |
      | beta     | 25001          | Test title     | 5     |


  Scenario: Delete registered hours successfully
    When coworker "beta" deletes worked hours today
      | Initials | Project Number | Activity Title |
      | beta     | 25001          | Test title     |
    Then the project 25001 activity "Test title" has no registered hours from coworker "beta" today

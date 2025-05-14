Feature: List Unregistered Activities Today
  Description: A coworker can view unregistered activities for today.
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

  Scenario: View unregistered activities for today
    When coworker "beta" views unregistered activities for today
    Then the system lists the following unregistered activities
      | Project Number | Activity Title |
      | 25001          | Test title     |
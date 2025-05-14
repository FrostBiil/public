Feature: List Registered Time
  Description: List the registered time of a coworker for all projects and activities.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name |
      | beta     | Beta |
    And the project system contains the following projects
      | Title     | Description    | Start Date | End Date   | Type   |
      | Project A | First project  | 01-05-2025 | 30-06-2025 | CLIENT |
      | Project B | Second project | 01-05-2025 | 31-07-2025 | INTERN |
    And coworker with initials "beta" is added to project 25001
    And coworker with initials "beta" is added to project 25002
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Finished | Description | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Design         | 15.0           | false    | UI work     | 25         | 1                 | 25       | 3               |
      | 25002          | Testing        | 10.0           | false    | QA work     | 25         | 2                 | 25       | 4               |
    And coworker "beta" registers work hours
      | Project Number | Activity Title | Date  | Hours |
      | 25001          | Design         | today | 4.0   |
      | 25002          | Testing        | today | 6.0   |
    And coworker with initials "beta" is logged in to project system

  Scenario: View registered time across all projects
    When coworker "beta" views their registered time
    Then the system lists the following time entries
      | Project Number | Activity Title | Date  | Hours |
      | 25001          | Design         | today | 4.0   |
      | 25002          | Testing        | today | 6.0   |

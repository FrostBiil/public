Feature: Delete Project
  Description: Delete a project from the system.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta |
    And coworker with initials "alph" is logged in to project system

  Scenario: Delete a project successfully
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
     | Test2 Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    When a project is deleted
      | Project Number |
      | 25001     |
    Then the project system has the following projects
      | Project Number | Title         | Description         | Type   |
      | 25002          | Test2 Project | Initial description | CLIENT |

  Scenario: Attempt to delete a project that does not exist
    Given the project system contains the following projects
      | Title        | Description         | Start Date  | End Date   | Type   |
      | Test Project | Initial description | 01-05- 2025 | 30-06-2025 | CLIENT |
    When a project is deleted
      | Project Number |
      | 25003     |
    Then the error message "Project cannot be null" is given

  Scenario: Attempt to delete a project that has an active activity assigned
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Test title     | Test Description | 25         | 1                 | 25       | 2               | 5              | 2              | false    |
    When a project is deleted
      | Project Number |
      | 25001     |
    Then the error message "Project has activities assigned to it, please remove them first" is given

  Scenario: Attempt to delete a project without being login
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And coworker logout
    When a project is deleted
      | Project Number |
      | 25001     |
    Then the error message "Please login" is given

  Scenario: Attempt to delete a project without being the project leader
      Given the project system contains the following projects
          | Title        | Description         | Start Date | End Date   | Type |
          | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
      And coworker with initials "beta" is logged in to project system
      And coworker with initials "beta" is added to project 25001
      And coworker with initials "beta" is assigned as project leader of project 25001
      And coworker with initials "alph" is logged in to project system
      When a project is deleted
          | Project Number |
          | 25001     |
      Then the error message "Only the project leader can delete a project" is given

  Scenario: Delete a project being the project leader
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
      | Test2 Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And coworker with initials "alph" is logged in to project system
    And coworker with initials "alph" is added to project 25001
    And coworker with initials "alph" is assigned as project leader of project 25001
    When a project is deleted
      | Project Number |
      | 25001     |
    Then the project system has the following projects
      | Project Number | Title        | Description           | Type   |
      | 25002          | Test2 Project | Initial description | CLIENT |
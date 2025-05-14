Feature: Delete Coworker
  Description: Delete a coworker from the system.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta |
    And coworker with initials "alph" is logged in to project system

  Scenario: Delete a coworker successfully
    Given the project system has the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta  |
    When a coworker is deleted
      | Initials |
      | beta     |
    Then the project system has the following coworkers
      | Initials | Name |
      | alph     | Alpha |

  Scenario: Attempt to delete a coworker that does not exist
    When a coworker is deleted
      | Initials |
      | gamma    |
    Then the error message "Coworker cannot be null" is given

  Scenario: Attempt to delete a coworker that is logged in
      When a coworker is deleted
      | Initials |
      | alph     |
      Then the error message "You cannot delete the user that is currently logged in" is given

  Scenario: Attempt to delete a coworker that has an active project activity assigned
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Test title     | Test Description | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    And coworker with initials "beta" is assigned to activity "Test title" in project 25001
    When a coworker is deleted
      | Initials |
      | beta    |
    Then the error message "Coworker is assigned to activities, please remove them first" is given

  Scenario: Attempt to delete a coworker that has an active fixed activity assigned
    Given a new fixed activity is created
      | Title        | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title  | false    | Fixed description | 01-05-2025 | 14-05-2025 | beta     |
    When a coworker is deleted
      | Initials |
      | beta    |
    Then the error message "Coworker is assigned to activities, please remove them first" is given

  Scenario: Attempt to delete a coworker that is a project leader
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And coworker with initials "beta" is logged in to project system
    And coworker with initials "beta" is added to project 25001
    And coworker with initials "beta" is assigned as project leader of project 25001
    And coworker with initials "alph" is logged in to project system
    When a coworker is deleted
      | Initials |
      | beta    |
    Then the error message "Coworker is a project leader in project (25001). Please remove them first" is given
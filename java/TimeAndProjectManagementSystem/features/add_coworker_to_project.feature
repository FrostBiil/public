Feature: Add coworker to project
  Description: Add coworkers to an existing project.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name |
      | beta     | Beta |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025  | CLIENT |

  Scenario: Add a coworker to a project
    Given the project 25001 exists
    When coworker with initials "beta" is added to project 25001
    Then the project 25001 has the following coworkers
      | Initials | Name  |
      | beta     | Beta  |

  Scenario: Add multiple coworkers to a project
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    Given the project 25001 exists
    When the following coworkers are added to project 25001
      | Initials |
      | alph     |
      | beta     |
    Then the project 25001 has the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta  |

  Scenario: Attempt to add a coworker who is already in the project
    Given the project 25001 exists
    And coworker with initials "beta" is already added to project 25001
    When coworker with initials "beta" is added again to project 25001
    Then the error message "Coworker is already part of the project" is given


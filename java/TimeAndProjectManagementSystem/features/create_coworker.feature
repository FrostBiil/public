Feature: Create Coworker
  Description: Create a new coworker in the system.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    And coworker with initials "alph" is logged in to project system

  Scenario: Create a new coworker successfully
    When a new coworker is created
      | Initials | Name  |
      | beta     | Beta  |
    Then the project system has the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta  |

  Scenario: Attempt to create a coworker with an existing initials
    Given a new coworker is created
      | Initials | Name  |
      | beta     | Beta  |
    When a new coworker is created
      | Initials | Name  |
      | beta     | Beta  |
    Then the error message "Initials already in use" is given

    Scenario: Attempt to create a coworker with an existing name
      Given a new coworker is created
        | Initials | Name  |
        | beta     | Beta  |
      When a new coworker is created
        | Initials | Name  |
        | ceta     | Beta  |
      Then the project system has the following coworkers
        | Initials | Name  |
        | alph     | Alpha |
        | beta     | Beta  |
        | ceta     | Beta  |

  Scenario: Attempt to create a coworker without initials
    When a new coworker is created
      | Initials | Name  |
      |      | Beta  |
    Then the error message "Initials cannot be empty" is given

  Scenario: Attempt to create a coworker without name
    When a new coworker is created
      | Initials | Name |
      | beta     |      |
    Then the error message "Name cannot be empty" is given

    Scenario: Attempt to create a coworker with more than 4 initials
    When a new coworker is created
      | Initials         | Name  |
      | toManyInitlas    | Beta  |
    Then the error message "Initials must be at most 4 characters" is given
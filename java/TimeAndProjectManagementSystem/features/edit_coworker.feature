Feature: Edit coworker
  Description: Edit coworker name
  Actor: Admin

  Background:
    Given the project system contains the following coworkers
      | Initials | Name   |
      | alph     | Alpha  |
    And coworker with initials "alph" is logged in to project system

  Scenario: Edit coworker name
    When the coworker "alph" field "name" is changed to "Alphonse"
    Then the coworker "alph" has name "Alphonse"

  Scenario: Invalid name (empty string)
    When the coworker "alph" field "name" is changed to ""
    Then the error message "Coworker type cannot be null or empty" is given
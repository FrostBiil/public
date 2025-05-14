Feature: Edit project
  Description: Update project title, description, and dates.
  Actor: Project Leader

  Background:
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    And coworker with initials "alph" is added to project 25001
    And coworker with initials "alph" is logged in to project system
    And coworker with initials "alph" is assigned as project leader of project 25001

  Scenario: Edit project title
    When the project title of 25001 is changed to "Updated Project Title"
    Then the project 25001 has title "Updated Project Title"

  Scenario: Edit project description
    When the project description of 25001 is changed to "Updated description"
    Then the project 25001 has description "Updated description"

  Scenario: Invalid project description (empty)
    When the project description of 25001 is changed to ""
    Then the error message "Project description cannot be empty" is given

  Scenario: Edit project start date
    When the project start date of 25001 is changed to "05-05-2025"
    Then the project 25001 has start date "05-05-2025"

  Scenario: Invalid project start date (empty)
    When the project start date of 25001 is changed to ""
    Then the error message "Invalid date format. Use dd-MM-yyyy" is given

  Scenario: Edit project end date
    When the project end date of 25001 is changed to "30-07-2025"
    Then the project 25001 has end date "30-07-2025"

  Scenario: Invalid project date update
    When the project end date of 25001 is changed to "01-04-2025"
    Then the error message "End date cannot be before start date" is given

  Scenario: Add Coworker to project
    Given the project system contains the following coworkers
      | Initials | Name |
      | beta     | Beta |
    When coworker with initials "beta" is added to project 25001
    Then project 25001 has the following coworkers
      | Initials |
      | alph     |
      | beta     |
  Scenario: Remove Coworker from project
    Given the project system contains the following coworkers
      | Initials | Name |
      | beta     | Beta |
    And coworker with initials "beta" is added to project 25001
    When coworker with initials "beta" is removed from project 25001
    Then project 25001 has the following coworkers
      | Initials |
      | alph     |
  Scenario: Edit project type to CLIENT
    When the project type of 25001 is changed to "CLIENT"
    Then the project 25001 has type "CLIENT"

  Scenario: Edit project type to INTERN
    When the project type of 25001 is changed to "INTERN"
    Then the project 25001 has type "INTERN"

  Scenario: Invalid project type (wrong type)
    When the project type of 25001 is changed to "INVALID_TYPE"
    Then the error message "Invalid value type for project type" is given

  Scenario: Edit project status to NOT_STARTED
    When the project status of 25001 is changed to "NOT_STARTED"
    Then the project 25001 has status "NOT_STARTED"

  Scenario: Edit project status to IN_PROGRESS
    When the project status of 25001 is changed to "IN_PROGRESS"
    Then the project 25001 has status "IN_PROGRESS"

  Scenario: Edit project status to COMPLETED
    When the project status of 25001 is changed to "COMPLETED"
    Then the project 25001 has status "COMPLETED"

  Scenario: Invalid project status (wrong status)
    When the project status of 25001 is changed to "INVALID_STATUS"
    Then the error message "Invalid value type for project status" is given
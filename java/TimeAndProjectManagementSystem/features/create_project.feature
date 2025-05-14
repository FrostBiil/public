Feature: Create a new project
  Description: Create a new project in the system without assigning additional details yet.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    And there are no existing projects
    And coworker with initials "alph" is logged in to project system

  Scenario: Create a new project successfully
    When a new project is created
      | Title        | Description         | Start Date | End Date   | Type   |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | Intern |
    Then the project system contains the following projects
      | Project Number | Title        | Description         | Start Date | End Date   | Type   |
      | 25001          | Test Project | Initial description | 01-05-2025 | 30-06-2025 | Intern |

    Scenario: Create a new project with no types defined should by default be Intern
      When a new project is created
        | Project Number | Title        | Description         | Start Date | End Date   | Type |
        | 25001          | Test Project | Initial description | 01-05-2025 | 30-06-2025 | INTERN |
      Then the project system contains the following projects
        | Project Number | Title        | Description         | Start Date | End Date   | Type   |
        | 25001          | Test Project | Initial description | 01-05-2025 | 30-06-2025 | Intern |

      Scenario: Create a new client project
        When a new project is created
          | Project Number | Title        | Description         | Start Date | End Date   | Type   |
          | 25001          | Test Project | Initial description | 01-05-2025 | 30-06-2025 | Client |
        Then the project system contains the following projects
          | Project Number | Title        | Description         | Start Date | End Date   | Type   |
          | 25001          | Test Project | Initial description | 01-05-2025 | 30-06-2025 | Client |

  Scenario: Attempt to create a project without a title
    When a new project with an empty title is created
    Then the error message "Project title is required" is given

    Scenario: Attempt to create a project with an invalid date range
    When a new project is created with start date "01-05-2025" and end date "30-04-2025"
    Then the error message "End date cannot be before start date" is given

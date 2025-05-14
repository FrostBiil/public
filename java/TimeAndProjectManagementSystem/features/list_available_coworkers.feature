Feature: List Available Coworkers
  Description: Project leader retrieves a list of available coworkers based on activity load.
  Actor: Project Leader

  Background:
    Given the project system contains the following projects
      | Title          | Description         | Start Date | End Date   | Type |
      | Test Project 1 | Initial description | 01-05-2025 | 30-06-2025 | Client |
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Type   | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Activity1      | 20             | INTERN | false    | Test Description | 25         | 1                 | 25       | 2               |
      | 25001          | Activity2      | 20             | INTERN | false    | Test Description | 25         | 1                 | 25       | 2               |
      | 25001          | Activity3      | 20             | INTERN | false    | Test Description | 25         | 2                 | 25       | 3               |
    And the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta  |
      | gamm     | Gamma |
    And coworker with initials "alph" is added to project 25001
    And coworker with initials "alph" is logged in to project system
    And coworker with initials "alph" is assigned as project leader of project 25001


  Scenario: Coworkers are available
    When "alph" requests a list of available coworkers for project 25001 activity "Activity1"
    Then the system displays the following employees as available:
      | Employee |
      | Alpha    |
      | Beta     |
      | Gamma    |

  Scenario: Some coworkers are available
    Given the project 25001 exists
    And the activity "Activity2" exists in project 25001
    When coworker with initials "alph" is assigned to activity "Activity2" in project 25001
    And coworker with initials "beta" is assigned to activity "Activity2" in project 25001
    When "alph" requests a list of available coworkers for project 25001 activity "Activity1"
    Then the system displays the following employees as available:
      | Employee |
      | Gamma    |

  Scenario: No coworkers are available
    Given the project 25001 exists
    And the activity "Activity2" exists in project 25001
    When coworker with initials "alph" is assigned to activity "Activity2" in project 25001
    And coworker with initials "beta" is assigned to activity "Activity2" in project 25001
    And coworker with initials "gamm" is assigned to activity "Activity2" in project 25001
    When "alph" requests a list of available coworkers for project 25001 activity "Activity1"
    Then the system displays the following employees as available:
      | Employee |


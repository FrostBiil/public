Feature: Assign coworker to activity
  Description: Assign a coworker to an activity within a project.
  Actor: Project Leader

  Background:
    Given the project system contains the following coworkers
      | Initials | Name |
      | beta     | Beta |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025  | CLIENT |
    And coworker with initials "beta" is added to project 25001
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | false    | Test Description | 25         | 1                 | 25       | 2               |

  Scenario: Assign a coworker to an activity
    Given the project 25001 exists
    And the activity "Test title" exists in project 25001
    When coworker with initials "beta" is assigned to activity "Test title" in project 25001
    Then the activity "Test title" has the following coworkers
      | Initials | Name  |
      | beta     | Beta  |

  Scenario: Attempt to assign a coworker already assigned to an activity
    Given the activity "Test title" in project 25001 already has coworker "beta" assigned
    When coworker "beta" is assigned to "Test title"
    Then the error message "Coworker is already assigned to the activity" is given

  Scenario: Attempt to assign a coworker to an activity with overlapping dates
    Given coworker "beta" is assigned to "Test title"
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Overlapping    | 20             | false    | Overlapping desc | 25         | 1                 | 25       | 2               |
    When coworker "beta" is assigned to "Overlapping"
    Then the error message "Coworker is assigned to another activity during this period" is given

    Scenario: Assign coworker to activity as another coworker
      Given the project system contains the following coworkers
        | Initials | Name |
        | zeta     | Zeta |
      And coworker with initials "beta" is logged in to project system
      When coworker with initials "zeta" is assigned to activity "Test title" in project 25001
      Then the activity "Test title" has the following coworkers
        | Initials | Name |
        | zeta     | Zeta |

      Scenario: retrieve a list of coworkers assigned to activity
        Given coworker with initials "beta" is logged in to project system
        And the project system contains the following coworkers
          | Initials | Name |
          | zeta     | Zeta |
        And coworker with initials "beta" is assigned to activity "Test title" in project 25001
        And coworker with initials "zeta" is assigned to activity "Test title" in project 25001
        Then the following coworkers are assigned to activity "Test title"
          | Initials | Name |
          | zeta     | Zeta |
          | beta     | Beta |

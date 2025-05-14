Feature: Prevent project assignment during fixed activities
  Description: Ensure coworkers cannot be assigned to activities while working on another activity.
  Actor: Project Leader

  Background:
    Given the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | Client |
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Type   | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | INTERN | false    | Test Description | 25         | 2                 | 25       | 3               |
    And the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta  |
      | gamm     | Gamma |
    And coworker with initials "alph" is assigned to projects
      | Project Number |
      | 25001          |
    And coworker with initials "beta" is assigned to projects
      | Project Number |
      | 25001          |
    And coworker with initials "alph" is assigned to activities
      | Project Number | Activity Title | Expected Hours | Type   | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | INTERN | false    | Test Description | 25         | 2                 | 25       | 3               |
    And the project system contains the following fixed activities
      | Coworker Initials | Activity Title | Finished | Description      | Start Date | End Date   |
      | alph              | Fixed Title    | false    | Test Description | 01-05-2025 | 14-05-2025 |
    And coworker "alph" is assigned as project leader for project number 25001
    And coworker with initials "alph" is logged in to project system

  Scenario: Prevent assignment to project activity while on another activity
    Given the project 25001 exists
    When a new activity with the following details
      | Project Number | Activity Title   | Description            | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Other Test title | Other Test Description | 25         | 3                 | 25       | 5               | 20             | 3              | false    |
    And coworker with initials "alph" tries to be assigned to activities
      | Project Number | Activity Title |
      | 25001          | Other Test title |
    Then the error message "Coworker is assigned to another activity during this period" is given
  Scenario: Prevent assignment to fixed activity while on another activity
    When the project system tries to create the following fixed activities
      | Coworker Initials | Activity Title  | Finished | Description      | Start Date | End Date   |
      | alph              | New Fixed Title | false    | Test Description | 13-05-2025 | 21-05-2025 |
    Then the error message "Assigned coworker is assigned to another activity during this period" is given

Feature: Delete Activity
  Description: Delete a activity from the system.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
      | beta     | Beta |
    And coworker with initials "alph" is logged in to project system
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And the project system contains the following project activities
      | Project Number | Activity Title | Expected Hours | Type   | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | INTERN | false    | Test Description | 25         | 2                 | 25       | 3               |
      | 25001          | Test title 2   | 50             | CLIENT | false    | Test Description | 25         | 2                 | 25       | 3               |
    And coworker with initials "alph" is assigned to projects
      | Project Number | Initials |
      | 25001          | alph     |
    And coworker with initials "beta" is assigned to projects
      | Project Number | Initials |
      | 25001          | alph     |
    And coworker with initials "alph" is assigned to activities
      | Project Number | Activity Title | Expected Hours | Type   | Finished | Description      | Start Year | Start Week Number | End Year | End Week Number |
      | 25001          | Test title     | 20             | INTERN | false    | Test Description | 25         | 2                 | 25       | 3               |
    And the project system contains the following fixed activities
      | Coworker Initials | Activity Title | Finished | Description      | Start Date | End Date   |
      | alph              | Fixed Title    | false    | Test Description | 01-05-2025 | 14-05-2025 |
      | beta              | Fixed Title 2  | false    | Test Description | 01-05-2025 | 14-05-2025 |

  Scenario: Delete a fixed Activity successfully
    Given the project system has the following fixed activities
      | Title         | Finished | Description      | StartDate  | EndDate    | Initials |
      | Fixed Title   | false    | Test Description | 01-05-2025 | 14-05-2025 | alph     |
      | Fixed Title 2 | false    | Test Description | 01-05-2025 | 14-05-2025 | beta     |
    When an activity is deleted
      | Title |
      | Fixed Title 2     |
    Then the project system has the following fixed activities
      | Title         | Finished | Description      | StartDate  | EndDate    | Initials |
      | Fixed Title 2 | false    | Test Description | 01-05-2025 | 14-05-2025 | beta     |

  Scenario: Attempt to delete a fixed activity that does not exist
      Given the project system has the following fixed activities
        | Title       | Finished | Description      | StartDate  | EndDate    | Initials |
        | Fixed Title | false    | Test Description | 01-05-2025 | 14-05-2025 | alph     |
    When an activity is deleted
          | Title |
          | Fixed Title 3     |
      Then the error message "Activity cannot be null" is given

  Scenario: Delete a project Activity successfully
    Given the project system has the following project activities
      | Project Number | Title        | Finished | Description      |
      | 25001          | Test title   | false    | Test Description |
      | 25001          | Test title 2 | false    | Test Description |
    When an activity is deleted
      | Title |
      | Test title |
    Then the project system has the following project activities
      | Project Number | Title        | Finished | Description      |
      | 25001          | Test title 2 | false    | Test Description |

  Scenario: Attempt to delete a project activity that does not exist
      Given the project system has the following project activities
        | Project Number | Title      | Finished | Description      |
        | 25001          | Test title | false    | Test Description |
    When an activity is deleted
          | Title |
          | Test title 3 |
      Then the error message "Activity cannot be null" is given
Feature: Edit activity
  Description: Update activity title, description, and dates.
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
      | gamm    | Gamma |
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
    And coworker "alph" is assigned as project leader for project number 25001
    And coworker with initials "alph" is logged in to project system


  Scenario: Edit activity start date
    When the activity "Fixed Title" start date is changed to "03-05-2025"
    Then the activity has start date "03-05-2025"

  Scenario: Invalid end activity dates
    When the activity "Fixed Title" start date is changed to "01-04-2027"
    Then the error message "start date can't be after end date" is given

  Scenario: Edit activity end date
    When the activity "Fixed Title" end date is changed to "01-04-2027"
    Then the activity has end date "01-04-2027"

  Scenario: Invalid end activity dates
    When the activity "Fixed Title" end date is changed to "01-04-2025"
    Then the error message "End date cannot be before start date" is given

  Scenario: Edit start year of a project activity
    When the project activity "Test title" field "startyear" is changed to "24"
    Then the project activity "Test title" has start year 24

  Scenario: Edit start week of a project activity
    When the project activity "Test title" field "startweek" is changed to "2"
    Then the project activity "Test title" has start week number 2

  Scenario: Edit end year of a project activity
    When the project activity "Test title" field "endyear" is changed to "26"
    Then the project activity "Test title" has end year 26

  Scenario: Edit end week of a project activity
    When the project activity "Test title" field "endweek" is changed to "3"
    Then the project activity "Test title" has end week number 3

  Scenario: Edit expected hours of a project activity
    When the project activity "Test title" field "expectedhours" is changed to "40.0"
    Then the project activity "Test title" has expected hours 40.0

  Scenario: Invalid start year (negative)
    When the project activity "Test title" field "startyear" is changed to "-1"
    Then the error message "Start year must be between 0 and 99" is given

  Scenario: Invalid start year (after end time)
    When the project activity "Test title" field "startyear" is changed to "98"
    Then the error message "Start time must be equal to or before end time" is given

  Scenario: Invalid start week (too high)
    When the project activity "Test title" field "startweek" is changed to "53"
    Then the error message "Start week must be between 1 and 52" is given

  Scenario: Start week after end week in same year
    When the project activity "Test title" field "startweek" is changed to "5"
    Then the error message "Start time must be equal to or before end time" is given

  Scenario: Invalid end year (too high)
    When the project activity "Test title" field "endyear" is changed to "100"
    Then the error message "End year must be between 0 and 99" is given

  Scenario: End year before start year
    When the project activity "Test title" field "endyear" is changed to "24"
    Then the error message "End time must be equal to or after start time" is given

  Scenario: Invalid end week (too high)
    When the project activity "Test title" field "endweek" is changed to "53"
    Then the error message "End week must be between 1 and 52" is given

  Scenario: Invalid end week (before start time)
    When the project activity "Test title" field "endweek" is changed to "1"
    Then the error message "End time must be equal to or before end time" is given

  Scenario: Invalid expected hours (negative)
    When the project activity "Test title" field "expectedhours" is changed to "-5.0"
    Then the error message "Expected hours must be greater than or equal to 0" is given

  Scenario: Edit description of a project activity
    When the project activity "Test title" field "description" is changed to "new description"
    Then the project activity "Test title" has description "new description"

  Scenario: Invalid description (number) which the system changes to String and sets as description
    When the project activity "Test title" field "description" is changed to 1
    Then the project activity "Test title" has description "1"

  Scenario: Invalid description (empty)
    When the project activity "Test title" field "description" is changed to ""
    Then the error message "Description cannot be null or empty" is given

  Scenario: Edit finished of a project activity (true)
    When the project activity "Test title" field "finished" is changed to "true"
    Then the project activity "Test title" is finished

  Scenario: Edit finished of a project activity (false)
    When the project activity "Test title" field "finished" is changed to "false"
    Then the project activity "Test title" is not finished

  Scenario: Invalid finished (invalid boolean)
    When the project activity "Test title" field "finished" is changed to "not a boolean"
    Then the error message "Invalid boolean value: not a boolean" is given

  Scenario: Invalid finished (empty)
    When the project activity "Test title" field "finished" is changed to ""
    Then the error message "Invalid boolean value: " is given

  Scenario: Edit Unknown field
    When the project activity "Test title" field "unknown" is changed to "value"
    Then the error message "Unknown field: unknown" is given

  Scenario: Edit Unknown activity
      When the project activity "Unknown title" field "startyear" is changed to "24" with null activity
      Then the error message "Activity cannot be null" is given

  Scenario: Another coworker other than project leader tries to edit project activity
    Given coworker logout
    And coworker with initials "gamm" is logged in to project system
    When the project activity "Test title" field "startyear" is changed to "24"
    Then the error message "Only the project leader can edit an activity" is given

  Scenario: Another coworker other than project leader that is assigned to the project tries to edit fixed activity
    Given coworker logout
    And coworker with initials "beta" is logged in to project system
    When the project activity "Test title" field "startyear" is changed to "24"
    Then the error message "Only the project leader can edit an activity" is given

  Scenario: project leader is null and a member edits project activity
    Given project leader for project number 25001 is removed
    And coworker logout
    And coworker with initials "beta" is logged in to project system
    When the project activity "Test title" field "description" is changed to "new description"
    Then the project activity "Test title" has description "new description"

  Scenario: project leader is null and a none member edits project activity
    Given project leader for project number 25001 is removed
    And coworker logout
    And coworker with initials "gamm" is logged in to project system
    When the project activity "Test title" field "description" is changed to "new description"
    Then the error message "You are not assigned to this activity and cannot edit it" is given
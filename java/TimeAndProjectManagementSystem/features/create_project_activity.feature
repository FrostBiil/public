Feature: Create activity for project
  Description: Create a new activity and add it to an existing project.
  Actor: Project Leader

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    And the project system contains the following projects
      | Title        | Description         | Start Date | End Date   | Type |
      | Test Project | Initial description | 01-05-2025 | 30-06-2025 | CLIENT |
    And coworker with initials "alph" is added to project 25001
    And coworker with initials "alph" is logged in to project system

  Scenario: Create a new activity for a project
    Given the project 25001 exists
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Test title     | Test Description | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    Then the project system has the following activities
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Test title     | Test Description | 25         | 1                 | 25       | 2               | 20             | 2              | false    |

  Scenario: Attempt to create an activity with invalid dates
    Given the project 25001 exists
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Test title     | Test Description | 25         | 3                 | 25       | 2               | 20             | 2              | false    |
    Then the error message "End date cannot be before start date" is given

  Scenario: Attempt to create an activity without title
    Given the project 25001 exists
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          |                | Test Description | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    Then the error message "Activity title cannot be empty" is given

  Scenario: Attempt to create activity with invalid start year
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Invalid Year   | Test Description | -1         | 1                 | 25       | 2               | 20             | 2              | false    |
    Then the error message "Start year must be between 0 and 99" is given

  Scenario: Attempt to create activity with invalid start week
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Invalid Week   | Test Description | 25         | 60                | 25       | 2               | 20             | 2              | false    |
    Then the error message "Start week number must be between 0 and 52" is given

  Scenario: Attempt to create activity with invalid end year
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Invalid End    | Test Description | 25         | 1                 | 100      | 2               | 20             | 2              | false    |
    Then the error message "End year must be between 0 and 99" is given

  Scenario: Attempt to create activity with negative expected hours
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Negative Hours | Test Description | 25         | 1                 | 25       | 2               | -5             | 2              | false    |
    Then the error message "Expected hours must be non-negative." is given

  Scenario: Attempt to create activity for unknown project
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 99999          | UnknownProject | Test Description | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    Then the error message "Project with number 99999 not found" is given

  Scenario: Attempt to create activity with null title
    When a new activity with the following details
      | Project Number | Activity Title | Description      | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          |                | Test Description | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    Then the error message "Activity title cannot be empty" is given

  Scenario: Attempt to create activity with duplicate title
    Given the project 25001 exists
    And a new activity with the following details
      | Project Number | Activity Title | Description | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | DuplicateTest  | Desc        | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    When a new activity with the following details
      | Project Number | Activity Title | Description | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | DuplicateTest  | Desc        | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    Then the error message "Activity already exists." is given

  Scenario: Attempt to create activity with null description
    When a new activity with the following details
      | Project Number | Activity Title | Description | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Title Test     |             | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    Then the error message "Description cannot be empty" is given

  Scenario: Attempt to create activity with negative end week
    When a new activity with the following details
      | Project Number | Activity Title | Description | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 25001          | Invalid End    | Desc        | 25         | 1                 | 25       | -1              | 20             | 2              | false    |
    Then the error message "End week number must be between 0 and 52" is given

  Scenario: Create activity with projectNumber zero (valid fallback project?)
    When a new activity with the following details
      | Project Number | Activity Title | Description | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 0              | NoProject      | Desc        | 25         | 1                 | 25       | 2               | 20             | 2              | false    |
    Then the project system has the following activities
      | Project Number | Activity Title | Description | Start Year | Start Week Number | End Year | End Week Number | Expected Hours | Expected Weeks | Finished |
      | 0              | NoProject      | Desc        | 25         | 1                 | 25       | 2               | 20             | 2              | false    |

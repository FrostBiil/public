Feature: Create Fixed Activity
  Description: Create a new fixed activity in the system. Such as Sickness, Vacation, Course, etc.
  Actor: Coworker

  Background:
    Given the project system contains the following coworkers
      | Initials | Name  |
      | alph     | Alpha |
    And coworker with initials "alph" is logged in to project system

  Scenario: Successfully create a Sick leave activity
    When a new fixed activity is created
      | Title        | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title  | false    | Fixed description | 01-05-2025 | 14-05-2025 | alph     |
    Then the project system has the following fixed activities
      | Title        | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title  | false    | Fixed description | 01-05-2025 | 14-05-2025 | alph     |

  Scenario: Vacation
    When a new fixed activity is created
      | Title       | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title | false    | Fixed description | 01-05-2025 | 14-05-2025 | alph     |
    Then the project system has the following fixed activities
      | Title       | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title | false    | Fixed description | 01-05-2025 | 14-05-2025 | alph     |

  Scenario: Successfully create a Course activity
    When a new fixed activity is created
      | Title       | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title | false    | Fixed description | 01-05-2025 | 14-05-2025 | alph     |
    Then the project system has the following fixed activities
      | Title       | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title | false    | Fixed description | 01-05-2025 | 14-05-2025 | alph     |

  Scenario: Attempt to create a fixed activity with no title
    When a new fixed activity is created
      | Title | Finished | Description       | StartDate  | EndDate    | Initials |
      |       | false    | Fixed description | 01-05-2025 | 14-05-2025 | alph     |
    Then the error message "Activity title cannot be empty" is given

  Scenario: Attempt to create a fixed activity with no description
    When a new fixed activity is created
      | Title       | Finished | Description | StartDate  | EndDate    | Initials |
      | Fixed Title | false    |             | 01-05-2025 | 14-05-2025 | alph     |
    Then the error message "Description cannot be empty" is given

    Scenario: Attempt to create a fixed activity with no start date
      When a new fixed activity is created
        | Title       | Finished | Description       | StartDate | EndDate    | Initials |
        | Fixed Title | false    | Fixed description |           | 14-05-2025 | alph     |
      Then the error message "Start and end dates cannot be null" is given

    Scenario: Attempt to create a fixed activity with no end date
      When a new fixed activity is created
        | Title       | Finished | Description       | StartDate  | EndDate | Initials |
        | Fixed Title | false    | Fixed description | 01-05-2025 |         | alph     |
      Then the error message "Start and end dates cannot be null" is given

  Scenario: Attempt to create a fixed activity with start date after end date
    When a new fixed activity is created
      | Title       | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title | false    | Fixed description | 15-05-2025 | 14-05-2025 | alph     |
    Then the error message "Start date cannot be after end date" is given

  Scenario: Attempt to create a fixed activity with no assigned coworker
    When a new fixed activity is created
      | Title       | Finished | Description       | StartDate  | EndDate    | Initials |
      | Fixed Title | false    | Fixed description | 01-05-2025 | 14-05-2025 |          |
    Then the error message "Assigned coworker cannot be null" is given
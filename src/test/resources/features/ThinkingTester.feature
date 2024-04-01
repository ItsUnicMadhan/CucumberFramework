Feature: Performing CRUD Operations Using Thinking Tester

  Scenario Outline: User calls web service to create a contact
    Given prepare the request body
      | firstName | lastName |
      | Expleo    | Mepz     |
    When create contact endpoint is invoked
    And verify the status code
      | StatusCode   |
      | <StatusCode> |
    Then verify contact created

    Examples: 
      | StatusCode |
      |        201 |

  Scenario Outline: User calls web service to get contact
    Given prepare the request
    When get contact endpoint is invoked
    And verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        200 |

  Scenario Outline: User calls web service to update a existing contact
    Given prepare the request body for updating the contact
      | lastName |
      | Chennai  |
    When update contact endpoint is invoked
    And verify the status code
      | StatusCode   |
      | <StatusCode> |
    Then verify contact updated

    Examples: 
      | StatusCode |
      |        200 |

  Scenario Outline: User calls web service to delete a exsiting contact
    Given prepare the request
    When delete contact endpoint is invoked
    And verify the status code
      | StatusCode   |
      | <StatusCode> |
    Then verify contact deleted

    Examples: 
      | StatusCode |
      |        200 |

Feature: Get book by ISBN

  Scenario Outline: User calls web service to get a book by its ISBN
    Given prepare the request
    When api is invoked
    Then verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        200 |

  Scenario Outline: User calls web service to get a book by its ISBN
    Given prepare the request
    When api is invoked
    Then verify the error code and error response
      | StatusCode   | ErrorKey   | ErrorValue   |
      | <StatusCode> | <ErrorKey> | <ErrorValue> |

    Examples: 
      | StatusCode | ErrorKey      | ErrorValue            |
      |        400 | error.message | Required parameter: q |

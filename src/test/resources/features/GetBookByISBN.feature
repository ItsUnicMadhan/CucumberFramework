Feature: Get book by ISBN

  Scenario Outline: User calls web service to get a book by its ISBN with Valid query params
    Given prepare the request with query param value "q" and value "isbn:9781451648546"
    When api is invoked
    Then verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        200 |

  Scenario Outline: User calls web service to get a book by its ISBN with Invalid query params
    Given prepare the request with query param value "a" and value "isbn:9781451648546"
    When api is invoked
    Then verify the error code and error response
      | StatusCode   | ErrorKey   | ErrorValue   |
      | <StatusCode> | <ErrorKey> | <ErrorValue> |

    Examples: 
      | StatusCode | ErrorKey      | ErrorValue            |
      |        400 | error.message | Required parameter: q |

  Scenario Outline: User calls reqres service to create a user detail with valid request
    Given prepare the request
    When post endpoint is invoked
    Then verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        201 |

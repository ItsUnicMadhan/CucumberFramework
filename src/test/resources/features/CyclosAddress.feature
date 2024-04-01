Feature: Performing CRUD Operations Using Cyclos

  Scenario Outline: User calls web service to create a Address
    Given prepare the request body
      | id                  | name | addressLine1 | city     | region  | country | latitude  | longitude |
      | 7762070814169944383 | Work | Mepz         | Tambaram | Chennai | IN      | 52.096175 |  5.116451 |
    When create address endpoint is invoked
    And verify the status code
      | StatusCode   |
      | <StatusCode> |
    Then verify address created

    Examples: 
      | StatusCode |
      |        201 |

  Scenario Outline: User calls web service to get Address
    Given prepare the request
    When get address endpoint is invoked "id"
    And verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        200 |

  Scenario Outline: User calls web service to delete a exsiting address
    Given prepare the request
    When delete address endpoint is invoked "id"
    And verify the status code
      | StatusCode   |
      | <StatusCode> |
    Then verify address deleted

    Examples: 
      | StatusCode |
      |        204 |

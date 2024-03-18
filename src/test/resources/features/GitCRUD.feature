Feature: Performing CRUD Operations Using Git

  Scenario Outline: User calls web service to get list of repo's
    Given prepare the request
    When getall repo endpoint is invoked 
    Then verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        200 |

  Scenario Outline: User calls web service to create a repo in github
    Given prepare the request body
      | name | description              | homepage           | private | has_issues | has_projects | has_wiki |
      | REST | Created from API Postman | https://github.com | false   | true       | true         | true     |
    When create repo endpoint is invoked
    Then verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        201 |

  
Scenario Outline: User calls web service to update a existing repo in github
    Given prepare the request body for updating the repo
      | name        | description              | homepage           | private | has_issues | has_projects | has_wiki |
      | RESTAssured | Created from API Postman | https://github.com | false   | true       | true         | true     |
    When update repo endpoint is invoked
    Then verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        200 |

  Scenario Outline: User calls web service to delete a exsiting repo in github
    Given prepare the request
    When delete repo endpoint is invoked
    Then verify the status code
      | StatusCode   |
      | <StatusCode> |

    Examples: 
      | StatusCode |
      |        204 |
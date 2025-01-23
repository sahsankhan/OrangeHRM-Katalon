Feature: Validate employee details via API

Scenario: Retrieve details for an existing employee
  Given I have a valid API key
  When I send a GET request for the employee details by ID 
  Then I should receive a 200 status code and the correct employee details
  
   Scenario: Retrieve details for a non-existent employee 
    Given I have a valid API key 
    When I send a GET request for an invalid employee ID 
    Then I should receive an error response with status code 422
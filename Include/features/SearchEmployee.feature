Feature: Search for an employee in the Employee List

Scenario: Search for an existing employee
Given I navigate to the login page
  When I enter credentials (username: "Admin", password: "admin123") and click login
  Then I should see the Dashboard
  When I navigate to the PIM module
  When I search for "John Doe" in the Employee Name field
  Then I should see "John Doe" in the search results

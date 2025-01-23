Feature: Add a new employee to the system

Scenario: Add an employee with valid details and a profile picture
  Given I navigate to the login page
  When I enter credentials (username: "Admin", password: "admin123") and click login
  Then I should see the Dashboard
  When I navigate to the PIM module
  And I click Add Employee
  And I fill in the required fields (First Name: "John", Last Name: "Doe") and upload a profile picture
  Then I should see the employee added successfully in the system
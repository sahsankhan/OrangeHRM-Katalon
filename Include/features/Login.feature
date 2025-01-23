Feature: Validate login functionality

Scenario: Login with valid credentials
  Given I navigate to the login page
  When I enter credentials (username: "Admin", password: "admin123") and click login
  Then I should see the Dashboard


Scenario: Login with invalid credentials
  Given I navigate to the login page
  When I enter credentials (username: "InvalidUser", password: "WrongPass") and click login
  Then I should see an error message saying Invalid credentials

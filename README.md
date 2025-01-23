# OrangeHRM Automated Testing: A BDD Implementation with Katalon Studio

## 1.	Project Setup
o	Created a new Katalon Studio project.
o	Organized folders for Test Cases, Test Suite, Object Repository, and Feature Files (BDD).
o	Added a StepDefinitions folder under Include/scripts/groovy to store step definition files.

## 2.	BDD Feature Files
o	Created feature files for each module:
o	Login.feature (for verifying login scenarios.)
o	AddEmployee.feature (for adding a new employee with file upload.)
o	SearchEmployee.feature (for searching the added employee.)
o	EmployeeAPI.feature (for API testing - GET requests to retrieve employee details).
o	Defined Gherkin steps (Given, When, Then) to describe the scenarios.

## 3.	Step Definitions
o	Implemented step definitions in Groovy, mapping each Gherkin step to Katalon Studio actions:
o	LoginSteps.groovy handles valid and invalid login steps.
o	AddEmployeeSteps.groovy handles adding an employee, uploading files, and searching in the Employee List.
o	APISteps.groovy handles sending GET requests to retrieve employee details and validate responses and POST requests to retrieve the valid API key.
o	SearchEmployee.groovy handles the steps for searching an employee.

## 4.	Web UI Automation
o	Used WebUI keywords (openBrowser, setText, click, verifyElementPresent, etc.) to automate the login, add employee, and search functionalities.
o	Captured locators using Katalon’s Object Repository (e.g., btn_Login, txt_Username, txt_Password, etc.).

## 5.	File Upload
o	Implemented file upload using WebUI.uploadFile 

## 6.	API Testing
o	Extracted the cookie and token value from login request
o	Authenticate using valid credentials
o	Extract the cookie response from the header to use and saved in the variable
o	Created a GET request object (GET_EmployeeByID) in the Object Repository for fetching employee details.
o	Used WS keywords (WS.sendRequest, WS.verifyResponseStatusCode) to validate status codes and JSON response data.


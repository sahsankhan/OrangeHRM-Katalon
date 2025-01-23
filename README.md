# OrangeHRM Automated Testing: A BDD Implementation with Katalon Studio

## Project Overview
This project demonstrates a Behavior-Driven Development (BDD) approach for automating OrangeHRM functionalities using Katalon Studio. It covers Web UI testing, API testing, and file upload operations.

---

## 1. Project Setup
- A new Katalon Studio project was created.
- The project structure includes folders for:
  - **Test Cases**
  - **Test Suites**
  - **Object Repository**
  - **Feature Files (BDD)**  
- A `StepDefinitions` folder was added under `Include/scripts/groovy` to store step definition files.

---

## 2. BDD Feature Files
Feature files were created for the following modules, written in Gherkin syntax:
- **Login.feature**  
  - Scenarios to verify login functionality with valid and invalid credentials.
- **AddEmployee.feature**  
  - Scenarios for adding a new employee, including file upload.
- **SearchEmployee.feature**  
  - Scenarios for searching an added employee.
- **EmployeeAPI.feature**  
  - Scenarios for API testing using GET requests to retrieve employee details.

---

## 3. Step Definitions
Step definitions in Groovy were implemented to map Gherkin steps to Katalon Studio actions:
- **LoginSteps.groovy**  
  - Handles login scenarios (valid/invalid credentials).
- **AddEmployeeSteps.groovy**  
  - Automates adding employees, uploading files, and verifying the Employee List.
- **APISteps.groovy**  
  - Manages API requests, including:
    - Sending GET requests to retrieve employee details.
    - Sending POST requests to fetch a valid API key.
- **SearchEmployeeSteps.groovy**  
  - Handles employee search steps.

---

## 4. Web UI Automation
- Automated functionalities using WebUI keywords such as:
  - `openBrowser`, `setText`, `click`, `verifyElementPresent`, etc.
- Locators were captured using Katalon’s Object Repository (e.g., `btn_Login`, `txt_Username`, `txt_Password`).

---

## 5. File Upload
- Implemented file upload functionality using `WebUI.uploadFile`.

---

## 6. API Testing
- Extracted cookies and token values from login requests.
- Authenticated using valid credentials and stored the cookie response in a variable.
- Created a GET request object (`GET_EmployeeByID`) in the Object Repository to fetch employee details.
- Validated API responses using WS keywords:
  - `WS.sendRequest`
  - `WS.verifyResponseStatusCode`
  - Verified JSON response data.

---

This project demonstrates how to integrate BDD principles with Katalon Studio for a robust automated testing approach.

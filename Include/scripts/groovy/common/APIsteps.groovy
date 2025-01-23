package common
import groovy.json.JsonSlurper
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import cucumber.api.java.en.Given
import cucumber.api.java.en.When
import cucumber.api.java.en.Then
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType

import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable


class Apisteps {

	String cookie

	@Given("I have a valid API key")
	def iHaveValidApiKeyAndCookie() {
		def loginRequest = new RequestObject('LoginRequest')
		loginRequest.setRestUrl('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login')
		loginRequest.setRestRequestMethod('GET')

		loginRequest.setHttpHeaderProperties([
			new TestObjectProperty('Accept', com.kms.katalon.core.testobject.ConditionType.EQUALS,
			'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*')
		])

		def loginResponse = WS.sendRequest(loginRequest)
		WS.verifyResponseStatusCode(loginResponse, 200)

		def responseBody = loginResponse.getResponseBodyContent()
		println "Login Response Body: ${responseBody}"

		def tokenRegex = /:token="&quot;([^&]+)&quot;"/
		def tokenMatch = (responseBody =~ tokenRegex)
		if (tokenMatch.find()) {
			GlobalVariable.token = tokenMatch.group(1)
			println "Extracted Token: ${GlobalVariable.token}"
		} else {
			throw new IllegalStateException("Token not found in login response.")
		}

		def setCookieHeader = loginResponse.getHeaderFields()?.get('Set-Cookie')
		if (setCookieHeader && !setCookieHeader.isEmpty()) {
			GlobalVariable.cookie = setCookieHeader[0].split(";")[0]
			println "Extracted Cookie: ${GlobalVariable.cookie}"
		} else {
			throw new IllegalStateException("Cookie not found in login response.")
		}

		def validateRequest = new RequestObject('ValidateRequest')
		validateRequest.setRestUrl("https://opensource-demo.orangehrmlive.com/web/index.php/auth/validate")
		validateRequest.setRestRequestMethod('POST')

		validateRequest.setHttpHeaderProperties([
			new TestObjectProperty('Accept', ConditionType.EQUALS, 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7'),
			new TestObjectProperty('Content-Type', ConditionType.EQUALS, 'application/x-www-form-urlencoded'),
			new TestObjectProperty('Cookie', ConditionType.EQUALS, GlobalVariable.cookie),
			new TestObjectProperty('Origin', ConditionType.EQUALS, 'https://opensource-demo.orangehrmlive.com'),
			new TestObjectProperty('Referer', ConditionType.EQUALS, 'https://opensource-demo.orangehrmlive.com/web/index.php/auth/login'),
			new TestObjectProperty('User-Agent', ConditionType.EQUALS, 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36'),
		])

		def requestBody = "_token=${GlobalVariable.token}&username=Admin&password=admin123"
		validateRequest.setBodyContent(new HttpTextBodyContent(requestBody, "UTF-8", "application/x-www-form-urlencoded"))

		def validateResponse = WS.sendRequest(validateRequest)

		println "Validation Response Status Code: ${validateResponse.getStatusCode()}"
		println "Validation Response Headers: ${validateResponse.getHeaderFields()}"
		println "Validation Response Body: ${validateResponse.getResponseBodyContent()}"

		if (validateResponse.getStatusCode() == 302) {
			def redirectUrl = validateResponse.getHeaderField("Location")
			println "Redirect URL: ${redirectUrl}"
		} else if (validateResponse.getStatusCode() != 200) {
			throw new IllegalStateException("Expected 200 but got ${validateResponse.getStatusCode()}")
		}

		def newSetCookieHeader = validateResponse.getHeaderFields()?.get('Set-Cookie')
		if (newSetCookieHeader && !newSetCookieHeader.isEmpty()) {
			GlobalVariable.newCookie = newSetCookieHeader[0].split(";")[0]
			println "New Cookie: ${GlobalVariable.newCookie}"
		} else {
			println "New cookie not found in validate response."
			throw new IllegalStateException("New cookie not found in validate response.")
		}
	}

	@When("I send a GET request for the employee details by ID")
	def sendGetRequestForEmployeeDetailsByID() {
		def employeeId = "1"
		def getEmployeeDetailsRequest = new RequestObject('GetEmployeeDetailsRequest')
		getEmployeeDetailsRequest.setRestUrl("https://opensource-demo.orangehrmlive.com/web/index.php/api/v2/pim/employees/${employeeId}")
		getEmployeeDetailsRequest.setRestRequestMethod('GET')

		getEmployeeDetailsRequest.setHttpHeaderProperties([
			new TestObjectProperty('Cookie', ConditionType.EQUALS, GlobalVariable.newCookie) // Use the new cookie
		])

		def response = WS.sendRequest(getEmployeeDetailsRequest)

		println "Request URL: ${getEmployeeDetailsRequest.getRestUrl()}"
		println "Request Headers: ${getEmployeeDetailsRequest.getHttpHeaderProperties()}"
		println "Response Status Code: ${response.getStatusCode()}"
		println "Response Body: ${response.getResponseBodyContent()}"

		if (response.getStatusCode() != 200) {
			throw new IllegalStateException("Expected 200 but got ${response.getStatusCode()}")
		}

		GlobalVariable.employeeDetailsResponse = response
	}



	@Then("I should receive a 200 status code and the correct employee details")
	def validateResponse() {
		def response = GlobalVariable.employeeDetailsResponse
		if (response == null) {
			throw new IllegalStateException("Response from the previous step is missing.")
		}

		def statusCode = response.getStatusCode()
		assert statusCode == 200 : "Expected status code 200 but got ${statusCode}"

		def responseBody = response.getResponseBodyContent()
		def parsedResponse = new groovy.json.JsonSlurper().parseText(responseBody)

		def expectedData = [
			data: [
				empNumber   : 1,
				lastName    : "Test",
				firstName   : "Orange",
				middleName  : "",
				employeeId  : "0001",
				terminationId: null
			],
			meta: [],
			rels: []
		]

		assert parsedResponse == expectedData : "Response body does not match expected data. Actual: ${parsedResponse}"
	}

	@When("I send a GET request for an invalid employee ID")
	def sendGetRequestForInvalidEmployeeID() {
		def invalidEmployeeId = "1000"
		def getInvalidEmployeeDetailsRequest = new RequestObject('GetInvalidEmployeeDetailsRequest')
		getInvalidEmployeeDetailsRequest.setRestUrl("https://opensource-demo.orangehrmlive.com/web/index.php/api/v2/pim/employees/${invalidEmployeeId}")
		getInvalidEmployeeDetailsRequest.setRestRequestMethod('GET')

		getInvalidEmployeeDetailsRequest.setHttpHeaderProperties([
			new TestObjectProperty('Cookie', ConditionType.EQUALS, GlobalVariable.newCookie) // Use the new cookie
		])

		def response = WS.sendRequest(getInvalidEmployeeDetailsRequest)

		println "Request URL: ${getInvalidEmployeeDetailsRequest.getRestUrl()}"
		println "Request Headers: ${getInvalidEmployeeDetailsRequest.getHttpHeaderProperties()}"
		println "Response Status Code: ${response.getStatusCode()}"
		println "Response Body: ${response.getResponseBodyContent()}"

		GlobalVariable.employeeDetailsResponse = response
	}

	@Then("I should receive an error response with status code 422")
	def verifyErrorResponseWithStatusCode422() {
		def response = GlobalVariable.employeeDetailsResponse  // Replace with the variable holding the last response

		if (response.getStatusCode() != 422) {
			throw new IllegalStateException("Expected status code 422, but got ${response.getStatusCode()}")
		}

		def expectedResponseBody = [
			"error": [
				"status": "422",
				"message": "Invalid Parameter",
				"data": [
					"invalidParamKeys": ["empNumber"]
				]
			]
		]

		def actualResponseBody = new groovy.json.JsonSlurper().parseText(response.getResponseBodyContent())

		if (!actualResponseBody.equals(expectedResponseBody)) {
			throw new IllegalStateException("Response body does not match expected response. \nExpected: ${expectedResponseBody} \nActual: ${actualResponseBody}")
		}

		println "Status code and response body verified successfully for error response with status code 422."
	}
}
package common
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import cucumber.api.java.en.Given
import cucumber.api.java.en.When
import cucumber.api.java.en.Then

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

public class LoginSteps {

	@Given("I navigate to the login page")
	def navigateToLoginPage() {
		WebUI.openBrowser('')
		WebUI.navigateToUrl('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login')
	}

	@When("I enter credentials \\(username: {string}, password: {string}) and click login")
	def enterValidCredentials(String username, String password) {
		WebUI.setText(findTestObject('Object Repository/Page_Login/input_Username_username'), username)
		WebUI.setText(findTestObject('Object Repository/Page_Login/input_Password_password'), password)
		WebUI.click(findTestObject('Object Repository/Page_Login/button_Login'))
	}


	@Then("I should see the Dashboard")
	def verifyDashboard() {
		WebUI.verifyElementVisible(findTestObject('Object Repository/Page_Login/h6_Dashboard'))
	}

	@Then("I should see an error message saying Invalid credentials")
	def verifyErrorMessage() {
		WebUI.verifyElementVisible(findTestObject('Object Repository/Page_Login/invalid_credentials_message'))
	}
}

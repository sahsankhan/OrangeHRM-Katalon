package common
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import cucumber.api.java.en.Given
import cucumber.api.java.en.When
import cucumber.api.java.en.Then
import com.kms.katalon.core.configuration.RunConfiguration

import java.awt.Robot
import java.awt.event.KeyEvent
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

public class AddEmployeeSteps {

	@When("I navigate to the PIM module")
	def navigateToPIMAndAddEmployee() {
		WebUI.click(findTestObject('Object Repository/Page_AddEmployee/a_PIM'))
	}

	@When("I click Add Employee")
	def clickAddEmployee() {
		WebUI.click(findTestObject('Object Repository/Page_AddEmployee/button_Add'))
	}

	@When("I fill in the required fields \\(First Name: {string}, Last Name: {string}) and upload a profile picture")
	def fillEmployeeDetails(String firstName, String lastName) {
		WebUI.setText(findTestObject('Object Repository/Page_AddEmployee/input_Employee Full Name_firstName'), firstName)
		WebUI.setText(findTestObject('Object Repository/Page_AddEmployee/input_Employee Full Name_lastName'), lastName)		
		String projectDir = RunConfiguration.getProjectDir()
		String filePath = projectDir + '/Include/myAvatar.png'
		WebUI.uploadFile(findTestObject('Object Repository/Page_AddEmployee/fileUpload'), filePath)
		
		
	}

	@Then("I should see the employee added successfully in the system")
	def verifyEmployeeAdded() {
		WebUI.click(findTestObject('Object Repository/Page_AddEmployee/button_Save'))
		WebUI.verifyElementVisible(findTestObject('Object Repository/Page_AddEmployee/p_Successfully Saved'))
	}
}

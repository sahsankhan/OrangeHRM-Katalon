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

public class SearchEmployee {

	@When("I search for {string} in the Employee Name field")
	def searchForEmployee(String employeeName) {
		WebUI.setText(findTestObject('Object Repository/Page_SearchEmployee/input'), employeeName)
		WebUI.click(findTestObject('Object Repository/Page_SearchEmployee/button_Search'))
	}

	@Then("I should see {string} in the search results")
	def verifySearchResult(String employeeName) {
		TestObject dynamicLocatorSearchedList = findTestObject('Object Repository/Page_SearchEmployee/div_John',
				[('employeeName') : employeeName])

		WebUI.verifyElementVisible(dynamicLocatorSearchedList)

		WebUI.click(dynamicLocatorSearchedList)

		TestObject dynamicLocatorSearchedProfile = findTestObject(
				'Object Repository/Page_SearchEmployee/h6_John Doe',
				[('employeeName') : employeeName])

		WebUI.verifyElementVisible(dynamicLocatorSearchedProfile)
	}
}

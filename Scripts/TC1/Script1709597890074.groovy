import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

TestObject makeTestObject(String id, String xpath) {
	TestObject tObj = new TestObject(id)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj	
}

String url = "https://kazurayam.github.io/ks_reading_pdf_from_blob_url/index.html"
TestObject tObj = makeTestObject("blob", "//*[@id='blobURL']")

WebUI.openBrowser(url)
WebUI.verifyElementPresent(tObj, 10);
WebUI.delay(3)
WebUI.closeBrowser()

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.ContentType
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

String url = "https://kazurayam.github.io/ks_reading_pdf_from_blob_url/index.html"
TestObject tObj = makeTestObject("blob", "//*[@id='blobURL']")

WebUI.openBrowser(url)
WebUI.verifyElementPresent(tObj, 10);

String text = WebUI.getText(tObj)
WebUI.comment(text)

Pattern pattern = Pattern.compile("blob:(.*)")
Matcher matcher = pattern.matcher(text)
if (matcher.matches()) {
	String httpsUrl = matcher.group(1)
	WebUI.comment(httpsUrl)
	downloadAndSave(httpsUrl, "./output/downloaded.pdf")
}

WebUI.closeBrowser()

/**
 * make a TestObject object 
 */
TestObject makeTestObject(String id, String xpath) {
	TestObject tObj = new TestObject(id)
	tObj.addProperty("xpath", ConditionType.EQUALS, xpath)
	return tObj
}

/**
 * 
 */
Path downloadAndSave(String url, String outfile) {
	Path file = Paths.get(outfile)
	Files.createDirectories(file.getParent())
	//
	CloseableHttpClient client = HttpClientBuilder.create().build();
	CloseableHttpResponse response = client.execute(new HttpGet(url));
	assert response.getStatusLine().getStatusCode() == 200 : url
	
	String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType()
	System.out.println("Content-Type: ${contentMimeType}")
	
	byte[] contentByteArray = EntityUtils.toByteArray(response.getEntity())
	InputStream istream = new ByteArrayInputStream(contentByteArray)
	
	Files.copy(istream, file)
	assert Files.exists(file)
}

package de.clear.it.test.sample;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.clear.it.test.abstrakt.AbstractWinTest;
import de.clear.it.test.data.Group;
import de.clear.it.test.pages.RechnerPage;
import io.appium.java_client.windows.WindowsDriver;

public class SampleDesktopTest extends AbstractWinTest {
	private WindowsDriver<WebElement> calculatorSession;

	@Test(groups = { Group.SYSTEMTEST }, description = "Starts google main page.")
	public void testFirst() throws Exception {
		WebElement calculatorResult;
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
			calculatorSession = new WindowsDriver<WebElement>(new URL("http://127.0.0.1:4723"), capabilities);
			calculatorSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

			calculatorResult = calculatorSession.findElementByAccessibilityId("CalculatorResults");
			Assert.assertNotNull(calculatorResult);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		calculatorSession.findElementByName("Eingabe löschen").click();
		Assert.assertEquals(_GetCalculatorResultText(), "0");

		calculatorSession.findElementByName("Eins").click();
		calculatorSession.findElementByName("Plus").click();
		calculatorSession.findElementByName("Sieben").click();
		calculatorSession.findElementByName("Gleich").click();
		Assert.assertEquals(_GetCalculatorResultText(), "8");

		calculatorResult = null;
		if (calculatorSession != null) {
			calculatorSession.quit();
		}
		calculatorSession = null;
	}

	protected String _GetCalculatorResultText() {
		WebElement calculatorResult = calculatorSession.findElementByAccessibilityId("CalculatorResults");
		// WebElement calculatorResult =
		// calculatorSession.findElement(By.id("CalculatorResults"));
		// trim extra text and whitespace off of the display value
		String result = calculatorResult.getText().replace("Die Anzeige lautet ", "").trim();
		return result.substring(0, result.length() - 1);
	}

	/**
	 * 
	 * Starts google main page
	 * 
	 * @throws URISyntaxException
	 * @throws InterruptedException
	 * 
	 */
	@Test(groups = { Group.SYSTEMTEST }, description = "Rechner Test.")
	public void testRechner() {
		RechnerPage rechnerPage = new RechnerPage(driver);
		rechnerPage = rechnerPage.doClickEingabeLoeschen();
		Assert.assertEquals(rechnerPage.doGetCalculatorResultText(), "0");
		rechnerPage = rechnerPage.doClickButtons("7+1=");
		Assert.assertEquals(rechnerPage.doGetCalculatorResultText(), "8");
	}
}

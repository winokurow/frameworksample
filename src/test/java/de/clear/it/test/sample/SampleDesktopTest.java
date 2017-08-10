package de.clear.it.test.sample;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.clear.it.test.data.Group;
import io.appium.java_client.windows.WindowsDriver;

public class SampleDesktopTest {
	private WindowsDriver calculatorSession;

	@Test(groups = { Group.SYSTEMTEST }, description = "Starts google main page.")
	public void testFirst() throws Exception {
		WebElement calculatorResult;
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
			calculatorSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
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
		// trim extra text and whitespace off of the display value
		String result = calculatorResult.getText().replace("Die Anzeige lautet ", "").trim();
		return result.substring(0, result.length() - 1);
	}
}

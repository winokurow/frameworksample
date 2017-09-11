package de.clear.it.test.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import de.clearit.test.data.LocatorTyp;
import de.clearit.test.framework.Check;
import de.clearit.test.framework.WinPageObject;
import de.clearit.test.framework.elemente.WinBaseElement;
import io.appium.java_client.windows.WindowsDriver;

/**
 * Die Hauptseite.
 * 
 * @author Ilja Winokurow
 */
public class RechnerPage extends WinPageObject {

	/* Button "Eingabe löschen" */
	@Check
	protected WinBaseElement eingabeLoeschenButton = new WinBaseElement("Eingabe löschen", LocatorTyp.NAME);

	/* Button "Eins" */
	protected WinBaseElement einsButton = new WinBaseElement("Eins", LocatorTyp.NAME);

	/* Button "Sieben" */
	protected WinBaseElement siebenButton = new WinBaseElement("Sieben", LocatorTyp.NAME);

	/* Button "Plus" */
	protected WinBaseElement plusButton = new WinBaseElement("Plus", LocatorTyp.NAME);

	/* Button "Gleich" */
	protected WinBaseElement gleichButton = new WinBaseElement("Gleich", LocatorTyp.NAME);

	/* Der Bereich 'Result' */
	protected WinBaseElement resultField = new WinBaseElement("CalculatorResults", LocatorTyp.ACCESSIBILITY_ID);

	/**
	 * Constructor.
	 * 
	 * @param driver
	 *            - driver
	 * 
	 */
	public RechnerPage(final WindowsDriver<WebElement> driver) {
		super(driver);
		logger = Logger.getLogger(this.getClass().getName());
		waitForMainElementsIsShown();
	}

	/**
	 * Den Button 'Eingabe löschen' betätigen
	 * 
	 * @return diese Seite
	 */
	public RechnerPage doClickEingabeLoeschen() {
		logger.info("Den Button 'Eingabe löschen' betätigen");
		eingabeLoeschenButton.click();
		return this;
	}

	/**
	 * Die Buttons betätigen
	 * 
	 * @param buttons
	 *            - die Buttons zu betätigen
	 * 
	 * @return diese Seite
	 */
	public RechnerPage doClickButtons(String buttons) {
		logger.info("Die Buttons " + buttons + " betätigen");
		String[] buttonsArray = buttons.split("");
		for (String button : buttonsArray) {
			switch (button) {
			case "1":
				einsButton.click();
				break;
			case "7":
				siebenButton.click();
				break;
			case "+":
				plusButton.click();
				break;
			case "=":
				gleichButton.click();
				break;
			}
		}
		return this;
	}

	public String doGetCalculatorResultText() {

		// trim extra text and whitespace off of the display value
		String result = resultField.getText().replace("Die Anzeige lautet ", "").trim();
		return result.substring(0, result.length() - 1);
	}
}

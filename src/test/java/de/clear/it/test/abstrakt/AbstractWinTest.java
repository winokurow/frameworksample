package de.clear.it.test.abstrakt;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import de.clearit.test.common.BasisLogger;
import de.clearit.test.framework.AllTestListenerAdapters;
import de.clearit.test.framework.WinPropertyManager;
import de.clearit.test.framework.windowsdriver.WinDriverManager;
import de.clearit.test.helper.WinTestHelper;
import io.appium.java_client.windows.WindowsDriver;

/**
 * AbstractTest.
 *
 * <P>
 * Das abstrakte Elternklass für alle Tests.
 *
 * @author Ilja Winokurow
 */
@Listeners(AllTestListenerAdapters.class)
public class AbstractWinTest extends WinTestHelper {

	/* Logger */
	protected final static Logger basisLogger = BasisLogger.LOGGER;

	/** Test gestartet */
	protected Boolean testStartet = null;

	/** Testschritt */
	protected Integer step = 0;

	/**
	 * Wird vor jedem Suite ausgeführt.
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) {
		basisLogger.info("Starte Testsuite");
	}

	/**
	 * startTest.
	 *
	 * initialize Execution Timer
	 * 
	 * @throws MalformedURLException
	 */
	@BeforeMethod(alwaysRun = true)
	public void beforeTest(Method method) throws MalformedURLException {
		driversBeingUsedInTest = new ArrayList<>();
		properties = WinPropertyManager.getInstance();

		final StringBuilder logMessage = new StringBuilder("Starte Testfall '")
				.append(method.getDeclaringClass().getSimpleName()).append(".").append(method.getName()).append("'");
		final Test annotation = method.getAnnotation(Test.class);
		if (annotation != null) {
			logMessage.append(" - ").append(annotation.description());
		}
		Reporter.log(logMessage.toString());
		basisLogger.info(logMessage);
		step = 1;
		String app = getProperty("app.id");
		erzeugeNeuenDriver(app);
	}

	/**
	 * Alle geöffnete Fenster schließen. Und WinDriver beenden.
	 */
	@Override
	public void stopWinDrivers() {
		if (driversBeingUsedInTest != null) {
			for (int i = 0; i < driversBeingUsedInTest.size(); i++) {
				stopWebDriver(driversBeingUsedInTest.get(i));
			}
		}
		driversBeingUsedInTest.clear();
	}

	/**
	 * Haupt Instance von WebDriver schließen.
	 */
	protected void stopWebDriver() {
		stopWebDriver(getDriver());
	}

	/**
	 * WebDriver schließen.
	 */
	protected void stopWebDriver(WindowsDriver<WebElement> driver) {
		WinDriverManager.stopDriver(driver);
		driversBeingUsedInTest.remove(driver);
		this.driver = null;
	}

	/**
	 * {@inheritDoc}
	 */
	public WindowsDriver getDriver() {
		return driver;
	}

	/**
	 * Logger für Testschritte
	 *
	 * @param text
	 *            - der Testschritt.
	 *
	 * @throws Exception
	 */
	@Override
	public void nextStep(String text) {
		step++;
		String prefix = "Step " + step + ": ";
		Reporter.log(text);
		basisLogger.info(prefix + text);
	}

	/**
	 * Zentrale Erzeugung eines neuen Drivers, die driver instanz darf nur hier
	 * neu zugewiesen werden!
	 *
	 * @param app
	 *            - App zu starten
	 * @throws MalformedURLException
	 */
	@Override
	protected void erzeugeNeuenDriver(String app) throws MalformedURLException {

		String appium = getProperty("appium.url");

		driver = WinDriverManager.createDriver(appium, app);
		driversBeingUsedInTest.add(driver);
	}

	/**
	 * Den Wert aus Property Datei auslesen
	 *
	 * @param key
	 *            - der Name des Wertes
	 * 
	 * @return ausgelesene Wert
	 */
	@Override
	public String getProperty(String key) {
		return getProperty(key, "");
	}

	/**
	 * Zentrale Erzeugung eines neuen Drivers, die driver instanz darf nur hier
	 * neu zugewiesen werden!
	 *
	 * @param url
	 *            - URL zu starten
	 */
	private String getProperty(String key, String defaultValue) {
		return getProperties().getProperty(key, defaultValue);
	}

	/**
	 * Properties Objekt zurückgeben
	 *
	 * @return Properties Objekt
	 */
	private WinPropertyManager getProperties() {
		if (properties == null) {
			properties = WinPropertyManager.getInstance();
		}
		return properties;
	}

}

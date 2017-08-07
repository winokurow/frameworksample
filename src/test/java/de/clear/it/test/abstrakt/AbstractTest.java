package de.clear.it.test.abstrakt;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import de.clearit.test.common.BasisLogger;
import de.clearit.test.common.ScreenshotCreator;
import de.clearit.test.common.TestUtils;
import de.clearit.test.common.WebdriverUtils;
import de.clearit.test.framework.AllTestListenerAdapters;
import de.clearit.test.framework.ExecutionTimer;
import de.clearit.test.framework.ExecutionTimerManager;
import de.clearit.test.framework.PropertyManager;
import de.clearit.test.framework.WebDriverCleanUp;
import de.clearit.test.framework.WebDriverHolder;
import de.clearit.test.framework.webdriver.WebDriverManager;
import de.clearit.test.framework.webdriver.WebDriverWrapper;
import de.clearit.test.helper.TestHelper;

/**
 * AbstractTest.
 *
 * <P>
 * Das abstrakte Elternklass für alle Tests.
 *
 * @author Ilja Winokurow
 */
@Listeners(AllTestListenerAdapters.class)
public class AbstractTest extends TestHelper
{

   /* Logger */
   protected final static Logger basisLogger = BasisLogger.LOGGER;

   /* Logger */
   protected final static Logger logger = Logger.getLogger("Test");

   /** Execution Timer. */
   ExecutionTimer executionTimer = null;

   /** Webdriver Instance. */
   private WebDriverWrapper driver;

   /** Alle im test erzeugten Webdriver */
   protected ArrayList<WebDriverWrapper> driversBeingUsedInTest = null;

   /** Die Eigenschaften des Tests. */
   protected PropertyManager properties;

   /** Test gestartet */
   protected Boolean testStartet = null;

   /** Testschritt */
   protected Integer step = 0;

   /**
    * Wird vor jedem Suite ausgeführt.
    */
   @BeforeSuite(alwaysRun = true)
   public void beforeSuite(ITestContext context)
   {
      basisLogger.info("Starte Testsuite");
   }
   
   /**
    * startTest.
    *
    * initialize Execution Timer
    */
   @BeforeMethod(alwaysRun = true)
   public void beforeTest(Method method)
   {
      driversBeingUsedInTest = new ArrayList<>();
      properties = PropertyManager.getInstance();

      final StringBuilder logMessage = new StringBuilder("Starte Testfall '")
            .append(method.getDeclaringClass().getSimpleName()).append(".").append(method.getName()).append("'");
      final Test annotation = method.getAnnotation(Test.class);
      if (annotation != null)
      {
         logMessage.append(" - ").append(annotation.description());
      }
      Reporter.log(logMessage.toString());
      basisLogger.info(logMessage);
      step = 1;
      initDynatraceMessung(this.getClass(), method);
      String url = getProperty("page.url");
	  erzeugeNeuenDriver(url);
   }

   /**
    * Wird nach jedem Test ausgeführt (Dynatrace Messung beenden) <br>
    */
   @AfterMethod(alwaysRun = true)
   public void afterTest()
   {
      beendeDynatraceMessung();
   }
   
   /**
    * Alle geöffnete Fenster schließen. Und WebDriver beenden.
    */
   @Override
   public void stopWebDrivers()
   {
      if (driversBeingUsedInTest != null)
      {
         for (int i = 0; i < driversBeingUsedInTest.size(); i++)
         {
            stopWebDriver(driversBeingUsedInTest.get(i));
         }
      }
      driversBeingUsedInTest.clear();
   }

   /**
    * Haupt Instance von WebDriver schließen.
    */
   protected void stopWebDriver()
   {
      stopWebDriver(getWebDriver());
   }

   /**
    * WebDriver schließen.
    */
   protected void stopWebDriver(WebDriverWrapper driver)
   {
      WebDriverManager.stopDriver(driver);
      driversBeingUsedInTest.remove(driver);
      this.driver = null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public WebDriverWrapper getWebDriver()
   {
      return driver;
   }

   /**
    * Logger für Testschritte
    *
    * @param text
    *           - der Testschritt.
    *
    * @throws Exception
    */
   public void nextStep(String text)
   {
      step++;
      String prefix = "Step " + step + ": ";
      Reporter.log(text);
      basisLogger.info(prefix + text);
   }

   /**
    * Browser starten und gewunschte Seite offnen.
    */
   protected void startSeite()
   {
      String url = holeBasisUrl();
      logger.info(String.format("Starte Seite %s", url));
      erzeugeNeuenDriver(url);

      // Manchmal hängt IE bei laden
      if (WebdriverUtils.isIE())
      {
         getWebDriver().get(url);
      }
   }

   /**
    * URL aus Property Datei auslesen.
    * 
    * @return ausgelesene URL
    */
   private String holeBasisUrl()
   {
      return getProperty("page.url");
   }

   /**
    * Zentrale Erzeugung eines neuen Drivers, die driver instanz darf nur hier neu zugewiesen werden!
    *
    * @param url
    *           - URL zu starten
    */
   protected void erzeugeNeuenDriver(String url)
   {
      if (ExecutionTimerManager.getExecutionTimer() == null)
      {
         executionTimer = new ExecutionTimer();
         executionTimer.init(TestUtils.getSubMethodName());
         ExecutionTimerManager.setExecutionTimer(executionTimer);
      }
      String grid = getProperty("seleniumgrid.url");
      String gridHint = getProperty("seleniumgrid.hint", "VDI");

      driver = WebDriverManager.createDriver(grid, gridHint, WebdriverUtils.isIE(), url);
      driversBeingUsedInTest.add(driver);
   }

   /**
    * Den Wert aus Property Datei auslesen
    *
    * @param key
    *           - der Name des Wertes
    * 
    * @return ausgelesene Wert
    */
   public String getProperty(String key)
   {
      return getProperty(key, "");
   }

   /**
    * Zentrale Erzeugung eines neuen Drivers, die driver instanz darf nur hier neu zugewiesen werden!
    *
    * @param url
    *           - URL zu starten
    */
   private String getProperty(String key, String defaultValue)
   {
      return getProperties().getProperty(key, defaultValue);
   }

   /**
    * Properties Objekt zurückgeben
    *
    * @return Properties Objekt
    */
   private PropertyManager getProperties()
   {
      if (properties == null)
      {
         properties = PropertyManager.getInstance();
      }
      return properties;
   }

   /**
    * Screenshot erzeugen
    * 
    * @param dateiname
    *           ohne Endung
    */
   protected void screenshotErzeugen(String dateiname)
   {
      new ScreenshotCreator().takeScreenshot(getWebDriver(), dateiname);
   }
}

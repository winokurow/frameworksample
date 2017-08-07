package de.clear.it.test.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.clearit.test.framework.Check;
import de.clearit.test.framework.elemente.GuiElement;
import de.clearit.test.pages.LoggedInPage;

/**
 * Die Hauptseite.
 * 
 * @author Ilja Winokurow
 */
public class MainPage  extends LoggedInPage {
	
	 /* Link "Clear-IT" */
	   @Check
	   protected GuiElement clearITLink = new GuiElement(By.id("site-it"));

	   /* Der Button "Weitere Stellenangebote" */
	   @Check
	   protected GuiElement weitereStellenangeboteButton = new GuiElement(By.cssSelector("a[class='joblist']"));

	   /**
	    * Constructor.
	    * 
	    * @param driver
	    *           - driver
	    * 
	    * */
	   public MainPage(final WebDriver driver, final LoggedInPage previousPage)
	   {
		   super(driver, previousPage, "Die Hauptseite");
		   logger = Logger.getLogger(this.getClass().getName());
		   waitForMainElementsIsShown();
	   }

	   /**
	    * Fenster schließen
	    * 
	    * @return die Seite "Clear-IT"
	    */
	   public ClearITPage gotoClearIT()
	   {
		   WebElement element = driver.findElement(By.id("site-it"));
		   element.click();
	      clickWithPageChange(clearITLink, "Die Maske 'Clear-IT' aufrufen.");
	      return new ClearITPage(driver, this, "");
	   }
}

package de.clear.it.test.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import de.clearit.test.framework.Check;
import de.clearit.test.framework.elemente.WebBaseElement;
import de.clearit.test.pages.LoggedInPage;

/**
 * Die Hauptseite.
 * 
 * @author Ilja Winokurow
 */
public class ClearITPage  extends LoggedInPage {
	
	 /* Link "Clear-IT" */
	   @Check
	   protected WebBaseElement infoText = new WebBaseElement(By.id("info"));

	   /**
	    * Constructor.
	    * 
	    * @param driver
	    *           - driver
	    * 
	    * */
	   public ClearITPage(final WebDriver driver, final LoggedInPage previousPage, String title)
	   {
		   super(driver, previousPage, title);
		   logger = Logger.getLogger(this.getClass().getName());
		   waitForMainElementsIsShown();
	   }
	   
	   /**
	    * Info auslesen
	    * 
	    * @return Info
	    */
	   public String doGetInfo()
	   {
	      String info = infoText.getText();
	      logger.info("Info = " + info);
	      return info;
	   }

}

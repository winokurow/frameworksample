package de.clear.it.test.sample;

import java.net.URISyntaxException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.clear.it.test.abstrakt.AbstractTest;
import de.clear.it.test.data.Group;
import de.clear.it.test.pages.ClearITPage;
import de.clear.it.test.pages.MainPage;

public class SampleWebTest extends AbstractTest
{

   /**
    * 
    * Starts google main page
    * 
    * @throws URISyntaxException
    * @throws InterruptedException
    * 
    */
   @Test(groups =
   { Group.SYSTEMTEST }, description = "Starts google main page.")
   public void testStartSeite() throws Exception
   {
      MainPage mainPage = new MainPage(getWebDriver(), null);
      mainPage.gotoClearIT();
      ClearITPage clearITPage = new ClearITPage(getWebDriver(), null, "");
      String info = clearITPage.doGetInfo();
      Assert.assertTrue(info.contains("Wie alle Unternehmen der CLEAR-GROUP steht die CLEAR IT"), "Info ist falsch");
   }

   /**
    * 
    * Starts google main page
    * 
    * @throws URISyntaxException
    * @throws InterruptedException
    * 
    */
   @Test(groups =
   { Group.SYSTEMTEST }, description = "Starts google main page.")
   public void testStartSeite1() throws Exception
   {
	   WebElement element1 = getWebDriver().findElement(By.id("site-it"));
	   element1.click();
	   WebElement element2 = getWebDriver().findElement(By.id("info"));

	   String info = element2.getText();
	   Assert.assertTrue(info.contains("Die Unternehmensgruppe steht für ausgeprägte"), "Info ist falsch");
   }

}

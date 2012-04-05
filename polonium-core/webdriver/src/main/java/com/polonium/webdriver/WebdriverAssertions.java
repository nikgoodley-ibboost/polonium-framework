package com.polonium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

/** Class defines assertion methods for webdriver, which throws assertion errors
 * 
 * @author Marek Serwanski
 */
public abstract class WebdriverAssertions {

	  /**Check if element is present on page - throws assertion error, so it means then-section will be notified
	   * @param by
	   * @return
	   */
	  public static void assertThatWebElementIsPresent(By by) {
	    try{
	    	DriverFactory.getDriver().findElement(by);
	    } catch(NoSuchElementException e){
	    	throw new AssertionError(e.getMessage());
	    }
	  }
	  
	  /** Search text in page source, throws assertion error, so it means then-section will be notified
	   * @param text
	   * @return
	   */
	  public static void assertThatTextIsPresentOnPage(String text) {
	    if(!DriverFactory.getDriver().getPageSource().contains(text)){
	    	throw new AssertionError("Text '" + text + "' cannot be found in page source.");
	    }
	  }
	  
	  /** Search in current page URL, throws assertion error, so it means then-section will be notified
	   * @param text
	   * @return
	   */
	  public static void assertThatURLcontains(String URLfragment) {
		 if(!DriverFactory.getDriver().getCurrentUrl().contains(URLfragment)){
		  	throw new AssertionError("URL of current page does not contain '" + URLfragment + "'.");
		  }
	  }
}

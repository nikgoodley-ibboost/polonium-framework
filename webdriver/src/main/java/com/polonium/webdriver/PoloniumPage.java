package com.polonium.webdriver;

import static com.polonium.webdriver.DriverFactory.getDriver;
import static com.polonium.webdriver.PoloniumWebdriverApi.BLANK_PAGE;
import static com.polonium.webdriver.PoloniumWebdriverApi.DEFAULT_PAGE_LOAD_AFTER_CLICK_TIMEOUT;
import static com.polonium.webdriver.PoloniumWebdriverApi.DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.polonium.core.exceptions.AnnotationException;
import com.polonium.core.exceptions.GivenException;
import com.polonium.core.exceptions.WhenException;
import com.polonium.webdriver.annotations.PageURL;

/** To create object, check: <br />
 * 1. if page is start point of test (first page to open) - that means URL in constructor, or @PageURL annotation must
 * be provided <br />
 * 2. if page is just another in flow - that means no URL need to be defined<br /><br />
 * 
 * Addidional, class provide simplicity of some common selenium actions
 * 
 * @author Marek Serwanski
 *
 */
public class PoloniumPage {
  protected WebDriver driver = DriverFactory.getDriver();
  private final Class<? extends PoloniumPage> page = this.getClass();
  protected static String URL;

  protected PoloniumPage() {
     //Checks if this page is first one on the test. If so then @PageURL annotation is required
     if (driver.getCurrentUrl().equals(BLANK_PAGE)) {
    	if (page.isAnnotationPresent(PageURL.class)) {
    	    openPage(page.getAnnotation(PageURL.class).value());
    	} else {
    	    throw new AnnotationException("Cannot start test with page:\n" +
    	     page.getName() + "\n because it has no URL defined in @PageURL annotation.\n" +
    	     		"(You can also use PoloniumPage(String PageURL))");
    	 }
      }
     readWebElementsMap();
  }

  protected PoloniumPage(String pageURL){
    openPage(pageURL);
    readWebElementsMap();
  }

  protected void openPage(String pageURL) {
    try {
        driver.get(pageURL);
    } catch (Exception e) {
        throw new GivenException("Driver cannot display page with given url: " + pageURL + "\n" + e.getMessage());
    }
  }
  
  protected void readWebElementsMap() {
	 try {
	      PageFactory.initElements(driver, this);
	 } catch (Exception e) {
	      throw new WhenException(e.getMessage());
	 }
  }

  /** Select element in dropdown list. Watch out - selenium is not perfect here, all selects are searched for element
   * @param selectOption
   * @throws WhenException
   */
  public void selectElementFromDropDownList(String selectOption) throws WhenException {
    final String optionTag = "option";
    for (WebElement option : driver.findElements(By.tagName((optionTag)))) {
      if ((option.getText().equals(selectOption))) {
        option.click();
        return;
      }
    }

    // choice was not found
    throw new WhenException("select option: " + selectOption +
      " was not found in any select on the page.");
  }

  /** Search text in page source 
   * @param text
   * @return
   */
  public boolean isTextPresentOnPage(String text) {
    return driver.getPageSource().contains(text);
  }
  
   /** Switch to window with the name that will contain a provided title. <br />
   * The window will be set as active (on top of others) and left as one
   */
  public void switchToWindow(String title) {
    switchToWindow(title, false);
  }
  
  /** Switch to window with the name that will contain a provided title. <br />
   * The window will be set as active (on top of others) and left as one.
   */
  public void switchToWindow(String title, boolean closeOtherWindows) {
    Set<String> windows = driver.getWindowHandles();
    for (String windowTitle : windows) {
      driver.switchTo().window(windowTitle);
      if (driver.getTitle().contains(title)) {
        break;
      } else if (closeOtherWindows) {
        driver.close();
      }
    }
  }
  
  /** Clicks on element and wait for page to load in default 5 seconds time. 
   * Method can be invoked when WebElement.click() or submit() does not work properly on elements
   * such as JSF dynamic links.
   * @param link
   */
  public void clickOnDynamicLink(WebElement link){
	  clickOnDynamicLink(link, DEFAULT_PAGE_LOAD_AFTER_CLICK_TIMEOUT);
  }
  
  /** Clicks on element and wait for page to load in provided time in seconds. 
   * Method can be invoked when WebElement.click() or submit() does not work properly on elements
   * such as JSF dynamic links.
   * @param link
   */
  public void clickOnDynamicLink(WebElement link, int timeoutInSeconds){
	  String currentUrl = driver.getCurrentUrl();
	  link.click();
	  
	  int timer = 0;
	  while(timer < timeoutInSeconds){
		  if(!currentUrl.equals(driver.getCurrentUrl())) return;
		  sleep(1);
		  timer++;
	  }
	  throw new GivenException("Unnable to load page after link click");
  }

  /** Simple force pause in provided time in seconds */
  public static void sleep(int timeInSeconds) {
    try {
      TimeUnit.SECONDS.sleep(timeInSeconds);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  /** Checks every second existance of element, quits after element is found or default 5 seconds timeout reached
   * 
   * @param webElement
   * @param timeoutInSeconds
   */
  protected static void waitForElement(By by) {
	  waitForElement(by, DEFAULT_WAIT_FOR_ELEMENT_TIMEOUT);
  }
  
  /** Checks every second existance of element, quits after element is found or timeout reached
   * 
   * @param webElement
   * @param timeoutInSeconds
   */
  protected static void waitForElement(By by, int maxTimeoutInSeconds) {
	  int timer = 0;
	  while(timer <= maxTimeoutInSeconds){
		  try{
			  sleep(1);
			  WebElement element = getDriver().findElement(by);
			  if(element != null) return;
		  } catch(Exception e){
			  timer++;
		  }
	  }
  }

  /**Can be used when WebElement cannot be mapped on object creation - for example when elements are created by javascript
   * after some user actions
   * @param by
   * @return
   */
  public static WebElement findWebElement(By by) {
    return getDriver().findElement(by);
  }
}

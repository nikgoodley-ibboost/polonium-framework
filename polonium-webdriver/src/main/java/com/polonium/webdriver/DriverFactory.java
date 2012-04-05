package com.polonium.webdriver;

import static com.polonium.webdriver.Browser.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/** Create once static Driver object. Type dependent on static DEFAULT_BROWSER field
 * 
 * @author Marek Serwanski
 */
public class DriverFactory {
	// default browser is firefox
	private static Browser DEFAULT_BROWSER = FIREFOX;
	private static WebDriver WEB_DRIVER;

	public static WebDriver getDriver() {
	  if(WEB_DRIVER == null){
		  switch(DEFAULT_BROWSER){
		  case FIREFOX :
			  WEB_DRIVER = new FirefoxDriver();
			  break;
		  case IEXPLORER :
			  WEB_DRIVER = new InternetExplorerDriver();
			  break;
		  case CHROME :
			  WEB_DRIVER = new ChromeDriver();
			  break;
		  }
	  }
	  return WEB_DRIVER;
  }

	public static void setBrowser(Browser browser) {
		DEFAULT_BROWSER = browser;
	}
}

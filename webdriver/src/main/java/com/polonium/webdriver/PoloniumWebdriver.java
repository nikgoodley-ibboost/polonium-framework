package com.polonium.webdriver;

import org.junit.After;
import org.junit.runner.RunWith;

import com.polonium.core.PoloniumTest;
import com.polonium.core.exceptions.ThenException;

/** Specify test runner and define JUnit after method which quits driver
 * 
 * @author Marek Serwanski
 */
@RunWith(PoloniumWebdriverRunner.class)
public class PoloniumWebdriver extends PoloniumTest{

	@After
	public void closeTestWindow(){
		try{
			DriverFactory.getDriver().quit();
		} catch(Exception e){
			throw new ThenException("Unnable to quit test");
		}
	}
}

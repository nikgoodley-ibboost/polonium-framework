package com.polonium.webdriver;

import org.junit.runner.Description;
import org.openqa.selenium.NotFoundException;

import com.polonium.core.PoloniumNotifier;
import com.polonium.core.PoloniumTestRunner;

/** Additional webdriver exceptions checkings 
 * 
 * @author Marek Serwanski
 *
 */
public class PoloniumWebdriverNotifier extends PoloniumNotifier{

	/** Check if NotFoundException occured and if so mark it as when failure */
	@Override
	public void checkException(Description description, Throwable e) {
		Throwable cause = e.getCause();
		
		if(PoloniumTestRunner.DETAILED_DESCRIPTION && 
			((cause instanceof NotFoundException) || (cause instanceof NullPointerException))){
			
			setDetailedWhenFailure(description, cause);
			testNotified = true;
		}
		
		super.checkException(description, e);
	}

}

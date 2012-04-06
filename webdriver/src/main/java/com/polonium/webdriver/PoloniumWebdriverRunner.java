package com.polonium.webdriver;

import com.polonium.core.PoloniumTest;
import com.polonium.core.PoloniumTestRunner;

/** This runner only applies WebDriverNotifier to original Polonium runner 
 * 
 * @author Marek Serwanski
 */
public class PoloniumWebdriverRunner extends PoloniumTestRunner{

	public PoloniumWebdriverRunner(Class<? extends PoloniumTest> testClass) {
		super(testClass);
		notifier = new PoloniumWebdriverNotifier();
	}
}

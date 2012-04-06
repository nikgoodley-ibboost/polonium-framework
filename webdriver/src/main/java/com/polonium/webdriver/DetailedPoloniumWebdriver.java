package com.polonium.webdriver;

import org.junit.runner.RunWith;

import com.polonium.core.PoloniumTestRunner;

/** Detailed description will be created. Other way to achieve this is using @DetailedDescription annotation
 * and extend just PoloniumTest
 * 
 * @author Marek Serwanski
 */
@RunWith(PoloniumWebdriverRunner.class)
public class DetailedPoloniumWebdriver extends PoloniumWebdriver{
	
	public DetailedPoloniumWebdriver(){
		PoloniumTestRunner.DETAILED_DESCRIPTION = true;
	}
}

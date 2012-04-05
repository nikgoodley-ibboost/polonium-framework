package com.polonium.core;

import org.junit.runner.RunWith;

/** Detailed description will be created. Other way to achieve this is using @DetailedDescription annotation
 * and extend just PoloniumTest
 * 
 * @author Marek Serwanski
 */
@RunWith(PoloniumTestRunner.class)
public class DetailedPoloniumTest {
	
	public DetailedPoloniumTest(){
		PoloniumTestRunner.DETAILED_DESCRIPTION = true;
	}
}

package com.polonium.core;

import junit.framework.AssertionFailedError;

import org.junit.runner.RunWith;

import com.polonium.core.annotations.MarkedThenFail;

/** Define runner
 * 
 * @author Marek Serwanski
 */
@RunWith(PoloniumTestRunner.class)
@MarkedThenFail({AssertionError.class, AssertionFailedError.class})
public class PoloniumTest {

}

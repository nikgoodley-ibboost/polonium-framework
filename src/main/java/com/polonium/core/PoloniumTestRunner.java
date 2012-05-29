package com.polonium.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import com.polonium.core.exceptions.AnnotationException;


/** Essential of polonium. Extends original JUnit runner and use its notifier. Methods reading and invoking
 * is here.
 * 
 * @author Marek Serwanski
 */
public class PoloniumTestRunner extends Runner {

	public static boolean DETAILED_DESCRIPTION = false;

	private PoloniumTest testInstance;
	protected DescriptionBuilder descriptionBuilder;
	protected PoloniumNotifier notifier;
	protected ExceptionsRecognizer exceptionsRecognizer;
	private List<Method> allMethods, tests;

	/** Creates necessary intances and build arrays of methods in test class */
	public PoloniumTestRunner(Class<? extends PoloniumTest> testClass) {
		testInstance = getTestInstance(testClass);
		exceptionsRecognizer = new ExceptionsRecognizer(testClass);
		descriptionBuilder = new DescriptionBuilder();
		notifier = new PoloniumNotifier();

		readMethods();
		descriptionBuilder.getDetailedDescriptionType(testClass);
		descriptionBuilder.createDescription(testClass, tests);
	}

	@Override
	public Description getDescription() {
		return descriptionBuilder.getClassDescription();
	}

	/** Start methods iteration and invokes */
	@Override
	public void run(RunNotifier runNotifier) {
		notifier.setRunNotifier(runNotifier);
		runMethods();
	}
	
	/** returns proper number of tests (not counting given/when/then descriptions as tests */ 
	@Override
	public int testCount() {
		return tests.size();
	}
	
	/** Flow: <br />
	 * 1. Invoke all methods annotated @Before <br />
	 * 2. Iterate all methods annotated @Test : <br />
	 * 2a. check if properly anntotated with @Given@When@Then <br />
	 * 2b. invoke <br />
	 * 2c. if exception occurs, then make proper notifications after exception recognizing <br />
	 * 2d. if no exception occurs, then notify with ok whole test description <br />
	 * 3. Invoke all after methods 
	 */
	protected void runMethods(){
		invokeMethods(BeforeClass.class);
		
		for (Method test : tests) {
			int testIndex = tests.indexOf(test);
			Description testDescription = descriptionBuilder.getTestRootDescription(testIndex);

			if (isReadyToRun(test, testDescription)) {
				try {
					// begin test running
					notifier.setStarted(testDescription);
					invokeMethods(Before.class);

					// run test - if no errors occurs then test is notified as
					// OK
					test.invoke(testInstance);
					notifier.setOK(testDescription);

				} catch (Exception e) {
					notifier.checkException(testDescription, e);
				} finally {
					invokeMethods(After.class);
				}
			}
		}
	}

	// checks if test is properly annotated and not set as Ignored
	private boolean isReadyToRun(Method test, Description testDescription) {
		if (!descriptionBuilder.isTestProperlyAnnotated(test)) {
			notifier.setFailure(testDescription, new AnnotationException());
			return false;
		} else if (test.isAnnotationPresent(Ignore.class)) {
			notifier.setIgnore(testDescription);
			return false;
		} else {
			return true;
		}
	}

	private void invokeMethods(Class<? extends Annotation> annotationType) {
		try {
			for (Method method : allMethods) {
				if (method.isAnnotationPresent(annotationType)) {
					method.invoke(testInstance);
				}
			}
		} catch (Exception e) {
			System.err.println("Error while invoking " + annotationType.getName() + " methods");
		}
	}

	private PoloniumTest getTestInstance(Class<? extends PoloniumTest> testClass) {
		try {
			return testClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void readMethods() {
		allMethods = Arrays.asList(testInstance.getClass().getMethods());
		tests = new ArrayList<Method>();
		for (Method method : allMethods) {
			if (method.isAnnotationPresent(Test.class)) {
				tests.add(method);
			}
		}
	}
}

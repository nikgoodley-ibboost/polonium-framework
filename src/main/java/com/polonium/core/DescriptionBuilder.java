package com.polonium.core;

import static com.polonium.core.PoloniumApi.DETAILED_DESCRIPTION_PROP_NAME;
import static com.polonium.core.PoloniumApi.GIVEN_PREFIX;
import static com.polonium.core.PoloniumApi.SEPARATOR;
import static com.polonium.core.PoloniumApi.THEN_PREFIX;
import static com.polonium.core.PoloniumApi.WHEN_PREFIX;
import static com.polonium.core.PoloniumTestRunner.DETAILED_DESCRIPTION;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.runner.Description;

import com.polonium.core.annotations.DetailedDescription;
import com.polonium.core.annotations.Given;
import com.polonium.core.annotations.Then;
import com.polonium.core.annotations.When;


/** Contains methods to build common and detailed test descriptions
 * 
 * @author Marek Serwanski
 */
public class DescriptionBuilder {
	
	private Description classDescription;
	private int testCounter = 0;
	
	public Description getClassDescription() {
		return classDescription;
	}
	
	/** Returns root description of selected index of test (from all tests description)
	 * @param index
	 * @return
	 */
	public Description getTestRootDescription(int index){
		return classDescription.getChildren().get(index);
	}
	
	/** Recognizes if detailed description schould be build. Try to read from: <br />
	 * system property or annotation @DetailedDescription present
	 */
	public void getDetailedDescriptionType(Class<? extends PoloniumTest> testClass) {
	    String property = System.getProperty(DETAILED_DESCRIPTION_PROP_NAME);
	    if ((property != null) && !property.equals(Boolean.FALSE.toString())) {
	      DETAILED_DESCRIPTION = true;
	    }
	    
	    if(testClass.isAnnotationPresent(DetailedDescription.class)){
	    	DETAILED_DESCRIPTION = true;
	    }
	 }
	
	/** creates full description of all test methods based on annotations Given, When, Then
	 * @param testClass
	 */
	public void createDescription(Class<? extends PoloniumTest> testClass, List<Method> tests) {
		classDescription = Description.createSuiteDescription(testClass.getName());
		
		for (Method test : tests) {
			if(!DETAILED_DESCRIPTION) createOneDescriptionFromAnnotations(test);
			else{
				Description testDescription = Description.createSuiteDescription(test.getName());
				classDescription.addChild(testDescription);
				
				if (isTestProperlyAnnotated(test)){
					testCounter++;
					createDescriptionsFromAnnotations(testClass, test, testDescription);
				}
			}
		}
	}
	
	/** If test is annotated with all mandatory annotations */
	public boolean isTestProperlyAnnotated(Method method) {
		return (method.isAnnotationPresent(Given.class) &&
				method.isAnnotationPresent(When.class) && 
				method.isAnnotationPresent(Then.class));
	}
	
	private void createDescriptionsFromAnnotations(Class<? extends PoloniumTest> testClass, Method test, Description testDescription) {
		String testNumber = getTestNumber();
		
		testDescription.addChild(Description.createTestDescription(testClass, 
				GIVEN_PREFIX + getGivenDescription(test) + testNumber));
		testDescription.addChild(Description.createTestDescription(testClass, 
				WHEN_PREFIX + getWhenDescription(test) + testNumber));
		testDescription.addChild(Description.createTestDescription(testClass, 
				THEN_PREFIX + getThenDescription(test) + testNumber));
	}
	
	private void createOneDescriptionFromAnnotations(Method test) {
		String descriptionValue = (
				test.getName() + 
				getTestNumber() +
				GIVEN_PREFIX + getGivenDescription(test) + SEPARATOR +
				WHEN_PREFIX + getWhenDescription(test) + SEPARATOR +
				THEN_PREFIX + getThenDescription(test)
			);
		
		Description testDescription = Description.createSuiteDescription(descriptionValue);
		classDescription.addChild(testDescription);
	}
	
	private String getTestNumber(){
		return " {" + testCounter + "} ";
	}
	
	private String getGivenDescription(Method test){
		return test.getAnnotation(Given.class).value();
	}
	
	private String getWhenDescription(Method test){
		return test.getAnnotation(When.class).value();
	}
	
	private String getThenDescription(Method test){
		return test.getAnnotation(Then.class).value();
	}
}

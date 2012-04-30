package com.polonium.core.example;

import junit.framework.Assert;

import org.junit.Test;

import com.polonium.core.PoloniumTest;
import com.polonium.core.annotations.DetailedDescription;
import com.polonium.core.annotations.Given;
import com.polonium.core.annotations.MarkedWhenFail;
import com.polonium.core.annotations.Then;
import com.polonium.core.annotations.When;

@DetailedDescription
@MarkedWhenFail({NullPointerException.class})
public class SimpleTest extends PoloniumTest{
	private static final String DEFAULT_MESSAGE = "test";
	
	@Given("simple service object")
	@When("doService method invoked")
	@Then("result of service is correct")
	@Test
	public void shouldServiceBeOK(){
		//given
		SimpleService simpleService = new SimpleService();
		
		//when
		String result = simpleService.doService(DEFAULT_MESSAGE);
		
		//then
		Assert.assertEquals("result: " + DEFAULT_MESSAGE, result);
	}
	
	@Given("simple service object")
	@When("doService method invoked")
	@Then("result of service is correct")
	@Test
	public void schouldServiceThrowGivenException(){
		//given
		SimpleService.serviceLocked = true;
		SimpleService simpleService = new SimpleService();
		
		//when
		String result = simpleService.doService(DEFAULT_MESSAGE);
		
		//then
		Assert.assertEquals("result: " + DEFAULT_MESSAGE, result);
	}
	
	@Given("simple service object")
	@When("doService method invoked")
	@Then("result of service is correct")
	@Test
	public void schouldServiceThrowWhenException(){
		//given
		SimpleService simpleService = new SimpleService();
		
		//when
		String result = simpleService.stimulateThrowWhenException();
		
		//then
		Assert.assertEquals("result: " + DEFAULT_MESSAGE, result);
	}
	
	@Given("simple service object")
	@When("doService method invoked")
	@Then("result of service is correct")
	@Test
	public void schouldServiceThrowThenException(){
		//given
		SimpleService simpleService = new SimpleService();
		
		//when
		String result = simpleService.stimulateThrowThenException();
		
		//then
		Assert.assertEquals("result: " + DEFAULT_MESSAGE, result);
	}
	
	@Given("simple service object")
	@When("doService method invoked")
	@Then("result of service is correct")
	@Test
	public void schouldServiceThrowNullPointerAsGiven(){
		//given
		SimpleService simpleService = new SimpleService();
		
		//when
		String result = simpleService.stimulateThrowNullPointer();
		
		//then
		Assert.assertEquals("result: " + DEFAULT_MESSAGE, result);
	}
}

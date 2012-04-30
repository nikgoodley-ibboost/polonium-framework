package com.polonium.core.example;

import com.polonium.core.exceptions.GivenException;
import com.polonium.core.exceptions.ThenException;
import com.polonium.core.exceptions.WhenException;

public class SimpleService {
	public static boolean serviceLocked = false;
	
	public SimpleService(){
		if(serviceLocked){
			SimpleService.serviceLocked = false;
			throw new GivenException("Cannot create instance, service was locked");
		}
	}
	
	/** returns string in format 'result: message' */
	public String doService(String message){
		return "result: " + message;
	}
	
	@SuppressWarnings("unused")
	public String stimulateThrowNullPointer(){
		if(true){
			throw new NullPointerException("Fake null pointer");
		}
		return "unreachable";
	}
	
	@SuppressWarnings("unused")
	public String stimulateThrowGivenException(){
		if(true){
			throw new GivenException("Fake given exception");
		}
		return "unreachable";
	}
	
	@SuppressWarnings("unused")
	public String stimulateThrowWhenException(){
		if(true){
			throw new WhenException("Fake when exception");
		}
		return "unreachable";
	}
	
	@SuppressWarnings("unused")
	public String stimulateThrowThenException(){
		if(true){
			throw new ThenException("Fake then exception");
		}
		return "unreachable";
	}
	
}

package com.polonium.core;

import java.util.ArrayList;
import java.util.List;

import com.polonium.core.annotations.MarkedGivenFail;
import com.polonium.core.annotations.MarkedThenFail;
import com.polonium.core.annotations.MarkedWhenFail;
import com.polonium.core.exceptions.GivenException;
import com.polonium.core.exceptions.ThenException;
import com.polonium.core.exceptions.WhenException;

/** Class creates three static lists with defined exception types to recognized 
 *
 * @author Marek Serwanski
 */
public class ExceptionsRecognizer {
	public static List<Class<? extends Exception>> markedWhenExceptions = new ArrayList<Class<? extends Exception>>();
	public static List<Class<? extends Exception>> markedGivenExceptions = new ArrayList<Class<? extends Exception>>();
	public static List<Class<? extends Exception>> markedThenExceptions = new ArrayList<Class<? extends Exception>>();

	public ExceptionsRecognizer(Class<? extends PoloniumTest> testClass){
		addDefaultExceptions();
		addProvidedExceptions(testClass);
	}

	private void addDefaultExceptions() {
		markedWhenExceptions.add(WhenException.class);
		markedGivenExceptions.add(GivenException.class);
		markedThenExceptions.add(ThenException.class);
	}
	
	private void addProvidedExceptions(Class<?> testClass) {
		if(testClass.isAnnotationPresent(MarkedGivenFail.class)){
			for(Class<? extends Exception> markedException : testClass.getAnnotation(MarkedGivenFail.class).value()){
				markedGivenExceptions.add(markedException);
			}
		}
		
		if(testClass.isAnnotationPresent(MarkedWhenFail.class)){
			for(Class<? extends Exception> markedException : testClass.getAnnotation(MarkedWhenFail.class).value()){
				markedWhenExceptions.add(markedException);
			}
		}
		
		if(testClass.isAnnotationPresent(MarkedThenFail.class)){
			for(Class<? extends Exception> markedException : testClass.getAnnotation(MarkedThenFail.class).value()){
				markedThenExceptions.add(markedException);
			}
		}
		
		//TODO: check why sometimes class returns annotations of its parent, sometimes not. And remove this shit! (oracle jdk6)
		Class<?> parent = testClass.getSuperclass();
		if(parent != null && !(parent.getName().equals("PoloniumTest"))){
			addProvidedExceptions(parent);
		}
	}
}

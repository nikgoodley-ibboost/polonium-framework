package com.polonium.core;

import static com.polonium.core.ExceptionsRecognizer.markedGivenExceptions;
import static com.polonium.core.ExceptionsRecognizer.markedThenExceptions;
import static com.polonium.core.ExceptionsRecognizer.markedWhenExceptions;

import java.util.List;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import com.polonium.core.exceptions.PoloniumException;


public class PoloniumNotifier {
	protected RunNotifier runNotifier;

	public void setRunNotifier(RunNotifier runNotifier) {
		this.runNotifier = runNotifier;
	}

	/** check type of exception and notify original JUnit with */
	public void checkException(Description description, Throwable e) {
		Throwable cause = e.getCause();
		Class<?> causeClass = cause.getClass();
		
		if (cause instanceof AssumptionViolatedException) {
			setIgnore(description);
			return;
		}

		if (!PoloniumTestRunner.DETAILED_DESCRIPTION) {
			setFailure(description, cause);
			return;
		} else{
			for(Class<? extends Exception> markedException : markedGivenExceptions){
				if(markedException.getName().equals(causeClass.getName())){
					setDetailedGivenFailure(description, cause);
					return;
				}
			}
			
			for(Class<? extends Exception> markedException : markedWhenExceptions){
				if(markedException.getName().equals(causeClass.getName())){
					setDetailedWhenFailure(description, cause);
					return;
				}
			}
			
			for(Class<? extends Exception> markedException : markedThenExceptions){
				if(markedException.getName().equals(causeClass.getName())){
					setDetailedThenFailure(description, cause);
					return;
				}
			}
			
			if(!((cause instanceof PoloniumException) || (cause instanceof AssertionError))){
				setFailure(description, cause);
			}
		}
	}
	
	protected void setDetailedGivenFailure(Description description, Throwable e){
		List<Description> descriptionChildren = description.getChildren();
		setFailure(descriptionChildren.get(0), e);
		setIgnore(descriptionChildren.get(1));
		setIgnore(descriptionChildren.get(2));
	}
	
	protected void setDetailedWhenFailure(Description description, Throwable e){
		List<Description> descriptionChildren = description.getChildren();
		setFailure(descriptionChildren.get(1), e);
		setOK(descriptionChildren.get(0));
		setIgnore(descriptionChildren.get(2));
	}

	protected void setDetailedThenFailure(Description description, Throwable e){
		List<Description> descriptionChildren = description.getChildren();
		setOK(descriptionChildren.get(0));
		setOK(descriptionChildren.get(1));
		setFailure(descriptionChildren.get(2), e);
	}
	
	protected void setFailure(Description description, Throwable e) {
		runNotifier.fireTestFailure(new Failure(description, e));

		for (Description childDescription : description.getChildren()) {
			setFailure(childDescription, e);
		}
	}

	public void setIgnore(Description description) {
		runNotifier.fireTestIgnored(description);

		for (Description childDescription : description.getChildren()) {
			setIgnore(childDescription);
		}
	}

	public void setStarted(Description description) {
		runNotifier.fireTestStarted(description);

		for (Description childDescription : description.getChildren()) {
			setStarted(childDescription);
		}
	}

	public void setOK(Description description) {
		runNotifier.fireTestFinished(description);

		for (Description childDescription : description.getChildren()) {
			setOK(childDescription);
		}
	}
}

package com.polonium.core.exceptions;

/** Exception to be thrown when mandatory polonium annotations are missing
 *  
 * @author Marek Serwanski
 * 
 */
public class AnnotationException extends PoloniumException{
	private static final long serialVersionUID = 1L;

	public AnnotationException(){
		super("Test Method not properly annotated.\nBe sure to put @Given @When @Then");
	}

	public AnnotationException(String msg){
		super(msg);
	}
}

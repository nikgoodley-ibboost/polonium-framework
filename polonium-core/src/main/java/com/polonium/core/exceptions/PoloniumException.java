package com.polonium.core.exceptions;

/** Father-marker for exceptions that are recognized by polonium test runner
 *  
 * @author Marek Serwanski
 */
public class PoloniumException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public PoloniumException(String message) {
		super(message);
	}
}

package com.polonium.core.exceptions;

/** Exception should be thrown on wrong test setup.
 *  
 * @author Marek Serwanski
 * 
 */
public class GivenException extends PoloniumException{
	private static final long serialVersionUID = 1L;

	public GivenException(String message) {
		super(message);
	}
}

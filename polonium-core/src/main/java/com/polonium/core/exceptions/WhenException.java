package com.polonium.core.exceptions;

/** Exception should be thrown on wrong test flow
 *  
 * @author Marek Serwanski
 * 
 */
public class WhenException extends PoloniumException{
	private static final long serialVersionUID = 1L;

	public WhenException(String message) {
		super(message);
	}
}

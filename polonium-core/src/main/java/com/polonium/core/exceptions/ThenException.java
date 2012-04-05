package com.polonium.core.exceptions;

/** Exception should be thrown on wrong test result / assertions
 *  
 * @author Marek Serwanski
 * 
 */
public class ThenException extends PoloniumException {
  private static final long serialVersionUID = 1L;

  public ThenException(String message) {
    super(message);
  }
}

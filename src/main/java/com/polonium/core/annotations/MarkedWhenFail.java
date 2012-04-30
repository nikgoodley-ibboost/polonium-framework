package com.polonium.core.annotations;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Mark list of exceptions that will be recognized as when fails
 *  
 * @author Marek Serwanski
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface MarkedWhenFail {
	public Class<? extends Exception>[] value();
}

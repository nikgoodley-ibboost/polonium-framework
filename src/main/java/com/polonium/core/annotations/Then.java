package com.polonium.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/** Mandatory annotation for every polonium test
 *  
 * @author Marek Serwanski
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( METHOD)
public @interface Then {
	String value() default "No value defined in @Then annotation";
}

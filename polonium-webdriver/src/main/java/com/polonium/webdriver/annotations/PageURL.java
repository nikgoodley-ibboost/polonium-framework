package com.polonium.webdriver.annotations;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Alternative for defining URL of PoloniumPage in constructor
 *  
 * @author Marek Serwanski
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface PageURL {
	String value() default "No @PageURL annotation defined on WebPage";
}

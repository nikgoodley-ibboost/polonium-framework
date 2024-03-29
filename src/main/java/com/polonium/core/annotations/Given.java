package com.polonium.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

/** Mandatory annotation for every polonium test
 *  
 * @author Marek Serwanski
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, LOCAL_VARIABLE })
public @interface Given {
  String value() default "No value defined in @Given annotation";
}

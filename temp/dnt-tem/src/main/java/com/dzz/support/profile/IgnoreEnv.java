package com.dzz.support.profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Profile;

/**
 * ignore profile
 *
 * @author Jeffrey
 * @since 2017/1/9 15:44
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Profile("ignore")
public @interface IgnoreEnv {

}

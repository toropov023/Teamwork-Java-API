package ca.toropov.api.teamwork.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: toropov
 * Date: 1/30/2019
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonBuildClass {
    /**
     * @return The parent json object name
     */
    String value();
}

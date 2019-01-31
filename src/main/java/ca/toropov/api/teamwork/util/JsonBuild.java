package ca.toropov.api.teamwork.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: toropov
 * Date: 1/30/2019
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonBuild {

    String value() default "";

    boolean ignore() default false;

    CollectionType collectionType() default CollectionType.COMMA_STRING;

    public enum CollectionType {
        /**
         * A single string separated by commas
         */
        COMMA_STRING,
        /**
         * An array of json objects
         */
        OBJECT_ARRAY;
    }
}

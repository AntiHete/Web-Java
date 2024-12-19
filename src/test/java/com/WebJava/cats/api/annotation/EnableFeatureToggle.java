package com.WebJava.cats.api.annotation;

import com.WebJava.cats.api.domain.Wearer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to enable features based on the given Wearer value.
 * Can be applied to methods or classes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface EnableFeatureToggle {

    /**
     * The Wearer value that determines which feature is enabled.
     */
    Wearer value();
}

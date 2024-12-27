package com.WebJava.cats.api.annotation;

import com.WebJava.cats.api.domain.Wearer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DisableFeatureToggle {
  Wearer value();
}

package com.WebJava.cats.api.dto.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Custom validation annotation to ensure that a string contains a cosmic word.
 * The word must contain terms like 'star', 'galaxy', or 'comet'.
 */
@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CosmicWordValidator.class)
@Documented
public @interface CosmicWordCheck {

  /**
   * Default error message when validation fails.
   */
  String NAME_SHOULD_CONTAIN_COSMIC_WORD = "Invalid Product Name: The field must contain a cosmic term (e.g., 'star', 'galaxy', 'comet')";

  /**
   * Customizable error message that can be overridden when validation fails.
   *
   * @return The message to be displayed on validation failure.
   */
  String message() default NAME_SHOULD_CONTAIN_COSMIC_WORD;

  /**
   * Grouping constraints can be used to apply validation rules to different validation groups.
   *
   * @return The groups for the validation.
   */
  Class<?>[] groups() default {};

  /**
   * Payload can carry additional data to the annotation. It's used for metadata.
   *
   * @return The payload.
   */
  Class<? extends Payload>[] payload() default {};
}

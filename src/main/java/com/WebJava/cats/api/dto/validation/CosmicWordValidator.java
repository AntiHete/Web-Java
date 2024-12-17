package com.WebJava.cats.api.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validator to check if a given string contains any of the predefined cosmic terms.
 */
public class CosmicWordValidator implements ConstraintValidator<CosmicWordCheck, String> {

  // List of cosmic terms to check in the string.
  private static final List<String> COSMIC_TERMS = Arrays.asList("star", "galaxy", "comet");

  // Pattern to match any of the cosmic terms in a string (case-insensitive).
  private static final String COSMIC_TERMS_REGEX = ".*\\b(" + String.join("|", COSMIC_TERMS) + ")\\b.*";
  private static final Pattern pattern = Pattern.compile(COSMIC_TERMS_REGEX, Pattern.CASE_INSENSITIVE);

  /**
   * Initializes the validator. This method can be left empty if there is no need for initialization.
   * 
   * @param constraintAnnotation The annotation that is being validated.
   */
  @Override
  public void initialize(CosmicWordCheck constraintAnnotation) {
    // No initialization needed for now
  }

  /**
   * Checks if the value contains any cosmic term.
   * 
   * @param value The string to validate.
   * @param context The context in which the validation is performed.
   * @return True if the value contains any cosmic term, false otherwise.
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return false; // Return false for empty or null values
    }
    return pattern.matcher(value).matches();
  }
}

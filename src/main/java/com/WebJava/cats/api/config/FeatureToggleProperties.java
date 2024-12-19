package com.WebJava.cats.api.config;

import java.util.Map;
<<<<<<< HEAD
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

=======

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to handle feature toggles.
 * It binds to properties with the prefix "application.feature" from the application configuration.
 */
>>>>>>> Lab3
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.feature")
public class FeatureToggleProperties {
<<<<<<< HEAD

    private static final Logger logger = LoggerFactory.getLogger(FeatureToggleProperties.class);

    private static final boolean DEFAULT_TOGGLE_VALUE = false;

    private Map<String, Boolean> toggles;

    public boolean check(String featureToggle) {
        return Optional.ofNullable(toggles)
                .map(map -> map.getOrDefault(featureToggle, DEFAULT_TOGGLE_VALUE))
                .orElseGet(() -> {
                    logger.warn("Feature toggle map is not initialized. Returning default value for '{}'", featureToggle);
                    return DEFAULT_TOGGLE_VALUE;
                });
    }
=======
  
  /**
   * Map to store feature toggles.
   * The key is the feature name, and the value is a boolean indicating if the feature is enabled.
   */
  private Map<String, Boolean> toggles;

  /**
   * Checks whether a specific feature toggle is enabled.
   *
   * @param featureToggle the name of the feature toggle
   * @return {@code true} if the feature is enabled, {@code false} otherwise
   */
  public boolean check(String featureToggle) {
    // Use getOrDefault to avoid NullPointerException in case the toggle is missing
    return toggles != null && toggles.getOrDefault(featureToggle, false);
  }
>>>>>>> Lab3
}

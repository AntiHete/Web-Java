package com.WebJava.cats.api.config;

import java.util.Map;


import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to handle feature toggles.
 * It binds to properties with the prefix "application.feature" from the application configuration.
 */

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.feature")
public class FeatureToggleProperties {

  
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

}

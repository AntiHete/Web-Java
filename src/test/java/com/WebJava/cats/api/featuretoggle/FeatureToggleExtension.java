package com.WebJava.cats.api.featuretoggle;

import com.WebJava.cats.api.annotation.DisableFeatureToggle;
import com.WebJava.cats.api.annotation.EnableFeatureToggle;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

<<<<<<< HEAD
=======
/**
 * JUnit 5 extension for managing feature toggles in tests.
 * - Enables or disables features before each test based on annotations.
 * - Restores feature toggle state after each test based on environment configuration.
 */
>>>>>>> Lab3
public class FeatureToggleExtension implements BeforeEachCallback, AfterEachCallback {

  @Override
  public void beforeEach(ExtensionContext context) {
    context.getTestMethod().ifPresent(method -> {
      FeatureToggleService featureToggleService = getFeatureToggleService(context);
<<<<<<< HEAD
      if (method.isAnnotationPresent(EnableFeatureToggle.class)) {
        EnableFeatureToggle enableFeatureToggle = method.getAnnotation(EnableFeatureToggle.class);
        featureToggleService.enable(enableFeatureToggle.value().getWearerName());
=======
      
      // Check if the method has an annotation to enable the feature
      if (method.isAnnotationPresent(EnableFeatureToggle.class)) {
        EnableFeatureToggle enableFeatureToggle = method.getAnnotation(EnableFeatureToggle.class);
        featureToggleService.enable(enableFeatureToggle.value().getWearerName());
        
      // Check if the method has an annotation to disable the feature
>>>>>>> Lab3
      } else if (method.isAnnotationPresent(DisableFeatureToggle.class)) {
        DisableFeatureToggle disableFeatureToggle = method.getAnnotation(DisableFeatureToggle.class);
        featureToggleService.disable(disableFeatureToggle.value().getWearerName());
      }
    });
  }

<<<<<<< HEAD
=======
  // Get the FeatureToggleService from the Spring application context
>>>>>>> Lab3
  private FeatureToggleService getFeatureToggleService(ExtensionContext context) {
    return SpringExtension.getApplicationContext(context).getBean(FeatureToggleService.class);
  }

  @Override
  public void afterEach(ExtensionContext context) {
    context.getTestMethod().ifPresent(method -> {
      String featureName = null;
<<<<<<< HEAD

=======
      
      // Determine the feature name from the annotation on the method
>>>>>>> Lab3
      if (method.isAnnotationPresent(EnableFeatureToggle.class)) {
        EnableFeatureToggle enabledFeatureToggleAnnotation = method.getAnnotation(EnableFeatureToggle.class);
        featureName = enabledFeatureToggleAnnotation.value().getWearerName();
      } else if (method.isAnnotationPresent(DisableFeatureToggle.class)) {
        DisableFeatureToggle disabledFeatureToggleAnnotation = method.getAnnotation(DisableFeatureToggle.class);
        featureName = disabledFeatureToggleAnnotation.value().getWearerName();
      }
<<<<<<< HEAD
      if (featureName != null) {
        FeatureToggleService featureToggleService = getFeatureToggleService(context);
=======

      // If a feature name was found, restore its toggle state based on the environment configuration
      if (featureName != null) {
        FeatureToggleService featureToggleService = getFeatureToggleService(context);
        
        // Determine whether the feature should be enabled or disabled based on the environment setting
>>>>>>> Lab3
        if (getFeatureNamePropertyAsBoolean(context, featureName)) {
          featureToggleService.enable(featureName);
        } else {
          featureToggleService.disable(featureName);
        }
      }
    });
  }

<<<<<<< HEAD
=======
  // Fetch the feature toggle state from the environment properties
>>>>>>> Lab3
  private boolean getFeatureNamePropertyAsBoolean(ExtensionContext context, String featureName) {
    Environment environment = SpringExtension.getApplicationContext(context).getEnvironment();
    return environment.getProperty("application.feature.toggles." + featureName, Boolean.class, Boolean.FALSE);
  }
}

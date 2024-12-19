package com.WebJava.cats.api.service.exception;

<<<<<<< HEAD
public class FeatureIsDisabledException extends RuntimeException {

    public static final String FEATURE_IS_DISABLED = "The feature '%s' is not available at the moment.";

    public FeatureIsDisabledException(String featureName) {
        super(String.format(FEATURE_IS_DISABLED, featureName));
    }

    public FeatureIsDisabledException() {
        super("The requested feature is not available at the moment.");
=======
/**
 * Custom exception that is thrown when a feature is disabled.
 * This exception is used to indicate that a requested feature is currently unavailable.
 */
public class FeatureIsDisabledException extends RuntimeException {

    // Template message to be used when formatting the exception message.
    public static final String FEATURE_IS_DISABLED = "The feature %s is not available at the moment";

    /**
     * Constructor that formats the exception message with the provided feature name.
     * 
     * @param message The name of the feature that is disabled.
     */
    public FeatureIsDisabledException(String message) {
        // Format the message with the provided feature name and pass it to the parent constructor.
        super(String.format(FEATURE_IS_DISABLED, message));
>>>>>>> Lab3
    }
}

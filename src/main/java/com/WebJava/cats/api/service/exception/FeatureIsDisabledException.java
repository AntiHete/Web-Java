package com.WebJava.cats.api.service.exception;


public class FeatureIsDisabledException extends RuntimeException {

    public static final String FEATURE_IS_DISABLED = "The feature '%s' is not available at the moment.";

    public FeatureIsDisabledException(String featureName) {
        super(String.format(FEATURE_IS_DISABLED, featureName));
    }

    public FeatureIsDisabledException() {
        super("The requested feature is not available at the moment.");

    public FeatureIsDisabledException(String message) {
        // Format the message with the provided feature name and pass it to the parent constructor.
        super(String.format(FEATURE_IS_DISABLED, message));

    }
}

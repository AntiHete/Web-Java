package com.WebJava.cats.api.web;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

import com.WebJava.cats.api.service.exception.*;
import com.WebJava.cats.api.util.InvalidatedParams;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    private static final String PRODUCT_NOT_FOUND = "product-not-found";
    private static final String DUPLICATE_NAME = "this-name-exists";
    private static final String PRICE_ADVISOR_ERROR = "price-advisor-error";
    private static final String FEATURE_DISABLED = "feature-disabled";
    private static final String VALIDATION_FAILED = "validation-failed";

    private static final String TITLE_PRODUCT_NOT_FOUND = "Product not found";
    private static final String TITLE_DUPLICATE_NAME = "Duplicate name";
    private static final String TITLE_PRICE_ADVISOR_ERROR = "Could not get price advice";
    private static final String TITLE_FEATURE_DISABLED = "Feature is disabled";
    private static final String TITLE_VALIDATION_FAILED = "Validation Failed";

    private ResponseEntity<ProblemDetail> buildResponse(HttpStatusCode status, String message, String type, String title) {
        ProblemDetail problemDetail = forStatusAndDetail(status, message);
        problemDetail.setType(create(type));
        problemDetail.setTitle(title);
        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFoundException(ProductNotFoundException ex) {
        return buildResponse(NOT_FOUND, ex.getMessage(), PRODUCT_NOT_FOUND, TITLE_PRODUCT_NOT_FOUND);
    }

    @ExceptionHandler(DuplicateProductNameException.class)
    public ResponseEntity<ProblemDetail> handleDuplicateProductNameException(DuplicateProductNameException ex) {
        return buildResponse(BAD_REQUEST, ex.getMessage(), DUPLICATE_NAME, TITLE_DUPLICATE_NAME);
    }

    @ExceptionHandler(ProductAdvisorApiException.class)
    public ResponseEntity<ProblemDetail> handleProductAdvisorApiException(ProductAdvisorApiException ex) {
        return buildResponse(ex.getStatus(), ex.getMessage(), PRICE_ADVISOR_ERROR, TITLE_PRICE_ADVISOR_ERROR);
    }

    @ExceptionHandler(FeatureIsDisabledException.class)
    public ResponseEntity<ProblemDetail> handleFeatureToggleNotEnabledException(FeatureIsDisabledException ex) {
        return buildResponse(NOT_FOUND, ex.getMessage(), FEATURE_DISABLED, TITLE_FEATURE_DISABLED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<InvalidatedParams> invalidParams = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> InvalidatedParams.builder()
                        .attribute(err.getField())
                        .cause(err.getDefaultMessage())
                        .build())
                .toList();

        ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, "Request validation failed");
        problemDetail.setType(create(VALIDATION_FAILED));
        problemDetail.setTitle(TITLE_VALIDATION_FAILED);
        problemDetail.setProperty("invalidParams", invalidParams);
        return ResponseEntity.status(BAD_REQUEST).body(problemDetail);
    }
}

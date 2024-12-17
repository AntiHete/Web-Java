package com.WebJava.cats.api.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductAdvisorApiException extends RuntimeException {
  
  private final HttpStatus status;

  public ProductAdvisorApiException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public ProductAdvisorApiException(HttpStatus status) {
    super(status.getReasonPhrase());
    this.status = status;
  }

  @Override
  public String toString() {
    return "ProductAdvisorApiException{" +
            "status=" + status +
            ", message='" + getMessage() + '\'' +
            '}';
  }

  @Override
  public String getMessage() {
    return super.getMessage() + " (HTTP Status: " + status + ")";
  }
}

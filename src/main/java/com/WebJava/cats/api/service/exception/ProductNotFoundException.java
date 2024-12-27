package com.WebJava.cats.api.service.exception;

public class ProductNotFoundException extends RuntimeException {

  public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id %s not found";
  public static final String PRODUCT_NOT_FOUND_DETAIL_MESSAGE = "Product with id %s and name '%s' not found";

  public ProductNotFoundException(Long productId) {
    super(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId));
  }

  public ProductNotFoundException(Long productId, String productName) {
    super(String.format(PRODUCT_NOT_FOUND_DETAIL_MESSAGE, productId, productName));
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}

package com.WebJava.cats.api.service;

import com.WebJava.cats.api.domain.product.Product;
import com.WebJava.cats.api.dto.product.advisor.ProductAdvisorResponseDto;

public interface ProductAdvisorService {

  ProductAdvisorResponseDto getProductPriceAdvice(Product product);
}

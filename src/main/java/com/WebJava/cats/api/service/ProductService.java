package com.WebJava.cats.api.service;

import com.WebJava.cats.api.domain.product.Product;
import com.WebJava.cats.api.dto.product.advisor.ProductAdvisorResponseDto;
import java.util.List;

public interface ProductService {
  List<Product> getProducts();
  Product getProduct(Long id);
  Product createProduct(Product product, Long categoryId);
  Product updateProduct(Long id, Product product, Long categoryId);
  void deleteProduct(Long id);


}

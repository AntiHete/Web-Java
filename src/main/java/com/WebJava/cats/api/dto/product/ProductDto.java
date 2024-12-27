package com.WebJava.cats.api.dto.product;

import com.WebJava.cats.api.domain.category.Category;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductDto {
  Long id;
  String name;
  String description;
  BigDecimal price;
  Integer stockQuantity;
  Category category;
}

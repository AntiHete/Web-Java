package com.WebJava.cats.api.domain.order;

import com.WebJava.cats.api.domain.product.Product;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class OrderEntry {

  Product product;
  Integer quantity;
}

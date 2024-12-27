package com.WebJava.cats.api.service;

import com.WebJava.cats.api.domain.product.Product;
import com.WebJava.cats.api.dto.product.advisor.ProductAdvisorRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductServiceMapper {

  ProductAdvisorRequestDto toProductAdvisorRequestDto(Product product);
}

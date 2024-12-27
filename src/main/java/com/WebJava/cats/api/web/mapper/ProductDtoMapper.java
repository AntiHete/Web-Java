package com.WebJava.cats.api.web.mapper;

import com.WebJava.cats.api.domain.product.Product;
import com.WebJava.cats.api.dto.product.ProductCreationDto;
import com.WebJava.cats.api.dto.product.ProductDto;
import com.WebJava.cats.api.dto.product.ProductUpdateDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    List<ProductDto> toProductDto(List<Product> products);

    ProductDto toProductDto(Product product);

    Product toProduct(ProductCreationDto productDto);
    Product toProduct(ProductUpdateDto productDto);

}

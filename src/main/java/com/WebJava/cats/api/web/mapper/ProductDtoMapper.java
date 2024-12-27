package com.WebJava.cats.api.web.mapper;

import com.WebJava.cats.api.domain.product.Product;
import com.WebJava.cats.api.dto.product.ProductCreationDto;
import com.WebJava.cats.api.dto.product.ProductDto;
import com.WebJava.cats.api.dto.product.ProductUpdateDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    // Преобразование списка продуктов в список DTO продуктов
    List<ProductDto> toProductDto(List<Product> products);

    // Преобразование одного продукта в DTO
    ProductDto toProductDto(Product product);

    // Преобразование ProductCreationDto в Product
    @Mapping(target = "id", ignore = true) // Пример того, как игнорировать поле ID при создании
    Product toProduct(ProductCreationDto productDto);

    // Преобразование ProductUpdateDto в Product
    @Mapping(target = "id", source = "productDto.id") // Пример явного указания источника и назначения
    Product toProduct(ProductUpdateDto productDto);

}

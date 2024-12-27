package com.WebJava.cats.api.web;

import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.domain.product.Product;
import com.WebJava.cats.api.dto.product.*;
import com.WebJava.cats.api.dto.product.advisor.ProductAdvisorResponseDto;
import com.WebJava.cats.api.service.ProductAdvisorService;
import com.WebJava.cats.api.service.ProductService;
import com.WebJava.cats.api.web.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ProductController.API_V1_PRODUCTS)
public class ProductController {

    // Константы для путей
    public static final String API_V1_PRODUCTS = "/api/v1/products";
    public static final String PRICE_ADVISOR = "/{id}/price-advisor";
    public static final String CATEGORY_PATH = "/category/{id}";
    public static final String UPDATE_CATEGORY_PATH = "/{id}/category/{categoryId}";
    public static final String WEARER_PATH = "/wearer/{wearer}";

    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;
    private final ProductAdvisorService productAdvisorService;

    private <T> ResponseEntity<T> wrapResponse(T body) {
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return wrapResponse(productDtoMapper.toProductDto(productService.getProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return wrapResponse(productDtoMapper.toProductDto(productService.getProduct(id)));
    }

    @GetMapping(PRICE_ADVISOR)
    public ResponseEntity<ProductAdvisorResponseDto> getProductWithPriceAdvisor(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        return wrapResponse(productAdvisorService.getProductPriceAdvice(product));
    }

    @PostMapping(CATEGORY_PATH)
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductCreationDto productDto, 
                                                    @PathVariable Long id) {
        Product createdProduct = productService.createProduct(productDtoMapper.toProduct(productDto), id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productDtoMapper.toProductDto(createdProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(UPDATE_CATEGORY_PATH)
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @PathVariable Long categoryId,
                                                    @RequestBody @Valid ProductUpdateDto productDto) {
        Product updatedProduct = productService.updateProduct(id, productDtoMapper.toProduct(productDto), categoryId);
        return wrapResponse(productDtoMapper.toProductDto(updatedProduct));
    }

    @GetMapping(WEARER_PATH)
    public ResponseEntity<List<ProductDto>> getProductsByWearer(@PathVariable String wearer) {
        Wearer validatedWearer = validateWearer(wearer);
        List<ProductDto> products = productService.getProductsByWearer(validatedWearer)
                .stream()
                .map(productDtoMapper::toProductDto)
                .toList();
        return wrapResponse(products);
    }

    private Wearer validateWearer(String wearer) {
        try {
            return Wearer.valueOf(wearer.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid wearer value: " + wearer);
        }
    }
}

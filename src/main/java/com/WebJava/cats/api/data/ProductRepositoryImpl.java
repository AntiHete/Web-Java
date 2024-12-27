package com.WebJava.cats.api.data;

import com.WebJava.cats.api.domain.Wearer;
import com.WebJava.cats.api.domain.category.Category;
import com.WebJava.cats.api.domain.product.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    private final List<Product> products = new ArrayList<>(buildAllProductsMock());

    @Override
    public Optional<Product> findById(Long id) {
        logger.info("Searching for product with ID: {}", id);
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        logger.info("Retrieving all products. Total count: {}", products.size());
        return new ArrayList<>(products); // Возвращаем копию списка для безопасности
    }

    @Override
    public Optional<Product> update(Long id, Product updatedProduct) {
        logger.info("Updating product with ID: {}", id);
        deleteById(id); // Удаляем существующий продукт
        products.add(updatedProduct); // Добавляем новый продукт
        return Optional.of(updatedProduct);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting product with ID: {}", id);
        var productToDelete = findById(id);
        if (productToDelete.isEmpty()) {
            logger.warn("Product with ID: {} not found. Skipping deletion.", id);
            return;
        }
        products.remove(productToDelete.get());
        logger.info("Product with ID: {} successfully deleted.", id);
    }

    @Override
    public Optional<Product> save(Product product) {
        logger.info("Adding new product: {}", product.getName());
        products.add(product);
        return Optional.of(product);
    }

    @Override
    public void clear() {
        logger.info("Resetting the product repository to default state.");
        products.clear();
        products.addAll(buildAllProductsMock());
    }

    private List<Product> buildAllProductsMock() {
        logger.info("Building initial mock data for products.");
        return List.of(
                Product.builder()
                        .id(1L)
                        .name("Star Helmet")
                        .description("A durable helmet for intergalactic travel.")
                        .price(BigDecimal.valueOf(299.99))
                        .stockQuantity(50)
                        .category(Category.builder().id(1L).name("Space Gear").build())
                        .wearer(Wearer.CATS)
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("Anti-Gravity Boots")
                        .description("Experience weightlessness on any surface.")
                        .price(BigDecimal.valueOf(199.99))
                        .stockQuantity(30)
                        .category(Category.builder().id(2L).name("Space Wear").build())
                        .wearer(Wearer.KITTIES)
                        .build(),
                Product.builder()
                        .id(3L)
                        .name("Star Map")
                        .description("A holographic map of the known universe.")
                        .price(BigDecimal.valueOf(149.99))
                        .stockQuantity(100)
                        .category(Category.builder().id(3L).name("Space Tools").build())
                        .wearer(Wearer.CATS)
                        .build()
        );
    }
}

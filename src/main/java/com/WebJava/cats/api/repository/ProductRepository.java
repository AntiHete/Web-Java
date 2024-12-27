package com.WebJava.cats.api.repository;

import com.WebJava.cats.api.repository.entity.ProductEntity;
import com.WebJava.cats.api.repository.projection.ProductProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing ProductEntity data.
 * Provides custom queries for finding products and their projections.
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /**
     * Finds a product by its name, ignoring case.
     * 
     * @param name The name of the product (case insensitive)
     * @return An Optional containing the product if found, or an empty Optional if not found
     */
    Optional<ProductEntity> findByNameIgnoreCase(String name);

    /**
     * Finds the most frequently ordered products based on the quantity ordered.
     * Uses a custom JPQL query to join the ProductEntity with the OrderEntryEntity,
     * group by product ID, and order by the sum of quantities in descending order.
     * 
     * @return A list of ProductProjection objects containing the most frequently ordered products
     */
    @Query("""
                SELECT p FROM ProductEntity p
                JOIN OrderEntryEntity oe ON p.id = oe.product.id
                GROUP BY p.id
                ORDER BY SUM(oe.quantity) DESC
            """)
    List<ProductProjection> findMostFrequentlyOrderedProduct();
}

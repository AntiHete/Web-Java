package com.WebJava.cats.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Custom repository interface for handling entities with natural IDs.
 * This interface extends JpaRepository and adds methods for retrieving
 * and deleting entities by their natural ID.
 * 
 * @param <T> The entity type
 * @param <ID> The type of the natural ID (unique identifier)
 */
@NoRepositoryBean
public interface NaturalIdRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * Finds an entity by its natural ID (a non-primary identifier).
     * 
     * @param naturalId The natural ID of the entity
     * @return An Optional containing the entity if found, or an empty Optional if not found
     */
    Optional<T> findByNaturalId(ID naturalId);

    /**
     * Deletes an entity by its natural ID.
     * If the entity is found by its natural ID, it is deleted from the repository.
     * 
     * @param naturalId The natural ID of the entity to be deleted
     */
    void deleteByNaturalId(ID naturalId);
}

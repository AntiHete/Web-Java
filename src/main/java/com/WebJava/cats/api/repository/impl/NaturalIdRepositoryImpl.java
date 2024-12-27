package com.WebJava.cats.api.repository.impl;

import com.WebJava.cats.api.repository.NaturalIdRepository;
import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * Implementation of a custom repository that supports natural ID lookups.
 * This class extends SimpleJpaRepository and implements NaturalIdRepository
 * to provide additional functionality for finding and deleting entities by their natural ID.
 * 
 * @param <T> The entity type
 * @param <ID> The ID type, which extends Serializable
 */
public class NaturalIdRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements NaturalIdRepository<T, ID> {

    private final EntityManager entityManager;

    /**
     * Constructor that initializes the repository with entity information and an EntityManager.
     * 
     * @param entityInformation Information about the entity type
     * @param entityManager Entity manager for interacting with the persistence context
     */
    public NaturalIdRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    /**
     * Finds an entity by its natural ID (a non-primary identifier that is unique for the entity).
     * 
     * @param naturalId The natural ID of the entity
     * @return An Optional containing the entity if found, or an empty Optional if not found
     */
    @Override
    public Optional<T> findByNaturalId(ID naturalId) {
        // Using Hibernate's bySimpleNaturalId method to load the entity by its natural ID
        return entityManager.unwrap(Session.class)
                .bySimpleNaturalId(this.getDomainClass())
                .loadOptional(naturalId);
    }

    /**
     * Deletes an entity by its natural ID.
     * If the entity is found by its natural ID, it is deleted from the repository.
     * 
     * @param naturalId The natural ID of the entity to be deleted
     */
    @Override
    public void deleteByNaturalId(ID naturalId) {
        // Find the entity and delete it if present
        findByNaturalId(naturalId).ifPresent(this::delete);
    }
}

package com.WebJava.cats.api.repository;

import com.WebJava.cats.api.repository.entity.OrderEntity;
import java.util.UUID;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing OrderEntity data.
 * Extends the NaturalIdRepository to support operations using the natural ID (cartId).
 */
@Repository
public interface OrderRepository extends NaturalIdRepository<OrderEntity, UUID> {

    // No additional methods are required, as NaturalIdRepository already provides methods
    // like findByNaturalId(ID naturalId) and deleteByNaturalId(ID naturalId).
}

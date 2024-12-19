package com.WebJava.cats.api.web.mapper;

import com.WebJava.cats.api.domain.order.OrderEntry;
import com.WebJava.cats.api.repository.entity.OrderEntity;
import com.WebJava.cats.api.repository.entity.OrderEntryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between OrderEntry and OrderEntryEntity.
 */
@Mapper(componentModel = "spring")
public interface OrderEntryMapper {

    /**
     * Converts an OrderEntry domain object to an OrderEntryEntity.
     * 
     * @param orderEntry the OrderEntry domain object to convert
     * @param order the associated OrderEntity to set in the OrderEntryEntity
     * @return the converted OrderEntryEntity
     */
    @Mapping(target = "order", source = "order")
    OrderEntryEntity toOrderEntryEntity(OrderEntry orderEntry, OrderEntity order);
}

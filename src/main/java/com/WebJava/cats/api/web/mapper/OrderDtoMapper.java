package com.WebJava.cats.api.web.mapper;

import com.WebJava.cats.api.domain.order.Order;
import com.WebJava.cats.api.domain.order.OrderContext;
import com.WebJava.cats.api.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between Order, OrderEntity, and OrderContext.
 */
@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

    /**
     * Converts an OrderEntity to an Order domain object.
     * 
     * @param orderEntity the OrderEntity to convert
     * @return the converted Order domain object
     */
    @Mapping(target = "entries", source = "orderEntries")
    Order toOrder(OrderEntity orderEntity);

    /**
     * Converts an Order domain object to an OrderEntity.
     * 
     * @param order the Order domain object to convert
     * @return the converted OrderEntity
     */
    @Mapping(target = "orderEntries", source = "entries")
    OrderEntity toOrderEntity(Order order);

    /**
     * Converts an Order domain object to an OrderContext.
     * 
     * @param order the Order domain object to convert
     * @return the converted OrderContext
     */
    OrderContext toOrderContext(Order order);
}

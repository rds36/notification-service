package com.rds.notificationservice.dto;

import java.time.Instant;
import java.util.List;


public record OrderCreatedEvent(
        Long orderId,
        Long restaurantId,
        Long userId,
        Long total,
        Instant createdAt,
        List<Item> items
){
    public record Item(Long menuItemId, String name, Integer qty, Long price){}
}

package com.cognizant.order.dto;

import com.cognizant.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Order order;
    private UserDto user;
}

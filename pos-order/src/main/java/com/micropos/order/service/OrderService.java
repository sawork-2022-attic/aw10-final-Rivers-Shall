package com.micropos.order.service;

import com.micropos.cart.model.Cart;
import com.micropos.order.model.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<Order> createOrder(Cart cart);

    Flux<Order> listOrders();

    Mono<Order> deliverById(Integer orderId);
}

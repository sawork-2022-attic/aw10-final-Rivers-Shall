package com.micropos.order.controller;

import com.micropos.api.OrdersApi;
import com.micropos.cart.mapper.CartMapper;
import com.micropos.cart.mapper.CartMapperImpl;
import com.micropos.cart.model.Cart;
import com.micropos.dto.CartDto;
import com.micropos.dto.OrderDto;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.model.Order;
import com.micropos.order.service.OrderService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class OrderController implements OrdersApi {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Override
    public Mono<ResponseEntity<OrderDto>> createOrder(Mono<CartDto> cartDto, ServerWebExchange exchange) {
        return cartDto.map(cartMapper::toCart)
                .flatMap(cart -> orderService.createOrder(cart))
                .map(orderMapper::toOrderDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<OrderDto>>> listOrders(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(orderService.listOrders().map(orderMapper::toOrderDto)));
    }
}

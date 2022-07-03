package com.micropos.order.service;

import com.micropos.cart.model.Cart;
import com.micropos.dto.OrderDto;
import com.micropos.order.model.Item;
import com.micropos.order.model.Order;
import com.micropos.order.repository.ItemRepository;
import com.micropos.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StreamBridge streamBridge;

    @Override
    public Mono<Order> createOrder(Cart cart) {
        Order order = new Order();
        order.status(OrderDto.StatusEnum.CREATED);
        order = orderRepository.save(order);
        List<Item> items = new ArrayList<>();
        for (com.micropos.cart.model.Item item : cart.items()) {
            Item item1 = new Item();
            item1.order(order)
                    .productId(item.productId())
                    .productName(item.productName())
                    .quantity(item.quantity())
                    .unitPrice(item.unitPrice());
            items.add(item1);
            itemRepository.save(item1);
        }
        order.items(items);
        order = orderRepository.save(order);
        return Mono.just(order);
    }

    @Override
    public Flux<Order> listOrders() {
        return Flux.fromIterable(orderRepository.findAll());
    }

    @Override
    public Mono<Order> deliverById(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return null;
        }
        Order order = orderOptional.get();
        order.status(OrderDto.StatusEnum.DELIVERED);
        return Mono.just(orderRepository.save(order));
    }
}

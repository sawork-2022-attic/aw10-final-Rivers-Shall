package com.micropos.cart.service;

import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartService {

    Double checkout(Cart cart);

    Mono<Double> checkout(Integer cartId);

    Mono<Cart> add(Cart cart, Item item);

    Mono<Cart> newCart();

    Flux<Cart> getAllCarts();

    Mono<Cart> getCart(Integer cartId);

    Integer test();
}

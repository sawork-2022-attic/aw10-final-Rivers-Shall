package com.micropos.cart.rest;

import com.micropos.api.CartsApi;
import com.micropos.cart.mapper.CartMapper;
import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import com.micropos.cart.service.CartService;
import com.micropos.dto.CartDto;
import com.micropos.dto.ItemDto;
import com.micropos.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
public class CartController implements CartsApi {

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public Mono<ResponseEntity<CartDto>> addItemToCart(Integer cartId, Mono<ItemDto> itemDto, ServerWebExchange exchange) {
        return cartService.getCart(cartId)
                .flatMap(cart -> itemDto.map(i -> cartMapper.toItem(i, cart))
                        .flatMap(item -> cartService.add(cart, item)))
                .map(cartMapper::toCartDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<CartDto>> createCart(ServerWebExchange exchange) {
        return cartService.newCart()
                .map(cartMapper::toCartDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<CartDto>>> listCarts(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(cartService.getAllCarts().map(cartMapper::toCartDto)));
    }

    @Override
    public Mono<ResponseEntity<CartDto>> showCartById(Integer cartId, ServerWebExchange exchange) {
        return cartService.getCart(cartId)
                .map(cartMapper::toCartDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Double>> showCartTotal(Integer cartId, ServerWebExchange exchange) {
        return cartService.checkout(cartId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}

package com.micropos.product.service;

import com.micropos.product.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {


    public Flux<Product> products();

    public Mono<Product> getProduct(String id);

    public Product randomProduct();
}

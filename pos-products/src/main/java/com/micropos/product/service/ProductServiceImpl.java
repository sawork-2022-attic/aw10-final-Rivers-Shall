package com.micropos.product.service;

import com.micropos.product.model.Product;
import com.micropos.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> products() {
        return Flux.fromIterable(productRepository.findAll());
    }

    @Override
    public Mono<Product> getProduct(String id) {
        return productRepository.findById(id)
                .map(Mono::just)
                .orElseGet(Mono::empty);
    }

    @Override
    public Flux<Product> searchProductByText(String searchText) {
        return Flux.fromIterable(productRepository.findAllByNameContains(searchText));
    }

    @Override
    public Product randomProduct() {
        return null;
    }
}

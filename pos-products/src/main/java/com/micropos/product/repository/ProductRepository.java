package com.micropos.product.repository;


import com.micropos.product.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {
    public List<Product> findAllByNameContains(String textSearch);
}
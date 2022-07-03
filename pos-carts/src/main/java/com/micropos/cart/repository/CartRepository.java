package com.micropos.cart.repository;

import com.micropos.cart.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CartRepository extends CrudRepository<Cart, Integer> {
}

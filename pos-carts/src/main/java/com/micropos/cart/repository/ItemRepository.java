package com.micropos.cart.repository;

import com.micropos.cart.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ItemRepository extends CrudRepository<Item, Integer> {
}

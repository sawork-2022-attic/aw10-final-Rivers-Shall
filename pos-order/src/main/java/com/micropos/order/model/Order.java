package com.micropos.order.model;

import com.micropos.cart.model.Cart;
import com.micropos.dto.OrderDto;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Accessors(fluent = true, chain = true)
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items;

    OrderDto.StatusEnum status;
}

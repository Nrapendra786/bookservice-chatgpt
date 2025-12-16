package com.techwaala.bookstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

//    // Bidirectional only where it really helps: Order -> LineItems
//    @OneToMany(
//            mappedBy = "order",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<OrderLineItem> lineItems = new ArrayList<>();
}



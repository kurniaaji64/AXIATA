package com.test.axiata.purchase_order.entity;

import java.time.LocalDateTime;

import com.test.axiata.items.entity.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name ="po_d")
@Entity
@Data
public class OrderDetails {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "item_id",nullable = false)
    Item items;

    @ManyToOne
    @JoinColumn(name = "poh_id",nullable = false)
    Orders order;
    private Integer item_qty;
    private Integer item_cost;
	private Integer item_price ;
}

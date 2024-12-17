package com.test.axiata.purchase_order.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name ="po_h")
@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	private LocalDateTime datetime;
    private String description;
	private Integer total_price;
    private Integer total_cost;
    private String ceated_by;
	private String updated_by;
	private LocalDateTime created_datetime;
	private LocalDateTime updated_datetime ;
    
    @PrePersist
    protected void onCreate() {
        this.datetime = LocalDateTime.now();
        this.created_datetime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_datetime = LocalDateTime.now();
    }
}

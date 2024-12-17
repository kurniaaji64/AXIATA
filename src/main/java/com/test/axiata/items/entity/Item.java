package com.test.axiata.items.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Table
@Entity
@Data
public class Item {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Integer price;
    private Integer cost;
    private String ceated_by;
	private String updated_by;
	private LocalDateTime created_datetime;
	private LocalDateTime updated_datetime ;
    
    @PrePersist
    protected void onCreate() {
        this.created_datetime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_datetime = LocalDateTime.now();
    }
}

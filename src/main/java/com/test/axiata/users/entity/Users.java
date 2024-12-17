package com.test.axiata.users.entity;

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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String first_name;
	private String last_name ;
	private String email ;
	private String phone ;
    private String ceated_by;
	private String updated_by;
	private LocalDateTime created_datetime;
	private LocalDateTime updated_datetime ;
    @Column (name = "is_deleted")
    private Boolean isDeleted ;
    
    @PrePersist
    protected void onCreate() {
        this.created_datetime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_datetime = LocalDateTime.now();
    }
}

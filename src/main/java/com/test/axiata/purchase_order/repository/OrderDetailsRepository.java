package com.test.axiata.purchase_order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.axiata.purchase_order.entity.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails , Integer>{
     @Query("SELECT i FROM OrderDetails i WHERE i.order.id IN :orderId")
    List<OrderDetails>findByOrderId(@Param("orderId")Integer orderId);
}

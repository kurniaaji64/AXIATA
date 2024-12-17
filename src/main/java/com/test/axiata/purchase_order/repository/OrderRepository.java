package com.test.axiata.purchase_order.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.axiata.purchase_order.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders , Integer> {
 
    @Query(value = """
            SELECT ph.id ,ph.description ,ph .total_price ,ph .total_cost, 
                JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'detail_id',pd.id,
                        'item_id', pd.item_id ,
                        'item_qty', pd.item_qty ,
                        'item_cost', pd.item_cost ,
                        'item_price', pd.item_price 
                        
                    ) 
                ) AS detailOrders
            FROM PO_H ph 
            LEFT JOIN PO_D pd ON pd.poh_id = ph .id 
            GROUP BY ph.id
            """, nativeQuery = true)
    List<Object[]> getOrderAndDetails();

    @Query(value = """
            SELECT ph.id ,ph.description ,ph .total_price ,ph .total_cost, 
                JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'detail_id',pd.id,
                        'item_id', pd.item_id ,
                        'item_qty', pd.item_qty ,
                        'item_cost', pd.item_cost ,
                        'item_price', pd.item_price 
                        
                    ) 
                ) AS detailOrders
            FROM PO_H ph 
            LEFT JOIN PO_D pd ON pd.poh_id = ph .id 
            WHERE ph.id = :orderId
            GROUP BY ph.id
            """, nativeQuery = true)
        List<Object[]> getOrderIdAndDetails(@Param("orderId") Integer orderId);
    
}

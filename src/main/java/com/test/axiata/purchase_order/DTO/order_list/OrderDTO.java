package com.test.axiata.purchase_order.DTO.order_list;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {
   private Integer id;
   private String  description;
    private Integer  total_price;
    private Integer  total_cost;
    private List<OrderDetailDTO> detailOrders;
}

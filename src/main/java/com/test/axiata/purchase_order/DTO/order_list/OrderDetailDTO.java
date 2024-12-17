package com.test.axiata.purchase_order.DTO.order_list;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// @AllArgsConstructor
public class OrderDetailDTO {
    private Integer detail_id;
    private Integer item_id;
    private Integer item_qty;
    private Integer item_cost;
    private Integer item_price;
}

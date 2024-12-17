package com.test.axiata.purchase_order.DTO.request.update;

import lombok.Data;

@Data
public class DataOrderDTO {
    private Integer order_detail_id;
    private Integer item_id;
    private Integer item_qty;

}

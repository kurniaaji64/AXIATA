package com.test.axiata.purchase_order.DTO.request;

import lombok.Data;

@Data
public class RequestOrderDTO {
    private String description;
    private Integer total_cost;
    private Integer total_price;
    private String ceated_by;
}

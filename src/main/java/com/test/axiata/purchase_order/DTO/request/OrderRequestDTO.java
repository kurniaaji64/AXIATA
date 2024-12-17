package com.test.axiata.purchase_order.DTO.request;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDTO {
    List<DataOrder> orders;
    private String description;
}

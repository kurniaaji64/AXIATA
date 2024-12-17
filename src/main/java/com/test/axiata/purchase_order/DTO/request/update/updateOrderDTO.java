package com.test.axiata.purchase_order.DTO.request.update;

import java.util.List;

import lombok.Data;

@Data
public class updateOrderDTO {
    List<DataOrderDTO> orders;
}

package com.test.axiata.items.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ItemRequestDTO {
    @NotBlank(message = "Name tidak boleh kosong")
    private String name;
    private String description;
    @NotNull(message = "Price tidak boleh kosong")
    private Integer price;
    @NotNull(message = "Cost tidak boleh kosong")
    private Integer cost;
}

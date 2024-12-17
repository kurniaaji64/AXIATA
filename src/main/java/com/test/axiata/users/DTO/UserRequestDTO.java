package com.test.axiata.users.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank(message = "First name tidak boleh kosong")
    private String first_name;
    @NotBlank(message = "First name tidak boleh kosong")
	private String last_name ;
    @Email(message = "Invalid format email")
	private String email ;
    @NotEmpty(message = "Phone tidak boleh kosong")
	private String phone ;
}

package com.charles.librarymgt.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateLibrarianRequest {
    @Email
    public String email;
    @NotBlank
    public String name;
    @NotBlank
    @Size(min = 6, max = 30)
    public String password;
}

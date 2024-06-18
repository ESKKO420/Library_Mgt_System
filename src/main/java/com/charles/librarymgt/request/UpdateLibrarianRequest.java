package com.charles.librarymgt.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateLibrarianRequest {

    @Email
    public String email;
    public String name;
    @Size(min = 6, max = 30)
    public String password;
}

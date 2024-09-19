package com.almousleck.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class RegistrationRequest {
    @NotEmpty(message = "firstname required")
    @NotBlank(message = "firstname is required")
    private String firstname;
    @NotEmpty(message = "lastname required")
    @NotBlank(message = "lastname is required")
    private String lastname;
    @NotEmpty(message = "email required")
    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email")
    private String email;
    @NotEmpty(message = "password required")
    @NotBlank(message = "password is required")
    @Size(min = 8, max = 12, message = "password should be 8 characters or long")
    private String password;
}

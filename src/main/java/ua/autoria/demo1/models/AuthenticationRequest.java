package ua.autoria.demo1.models;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;
}

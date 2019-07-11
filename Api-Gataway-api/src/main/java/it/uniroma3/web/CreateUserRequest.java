package it.uniroma3.web;


import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
}

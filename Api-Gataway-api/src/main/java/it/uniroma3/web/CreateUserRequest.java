package it.uniroma3.web;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
}

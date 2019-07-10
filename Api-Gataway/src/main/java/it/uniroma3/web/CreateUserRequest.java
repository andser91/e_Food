package it.uniroma3.web;


import lombok.Data;
import lombok.Setter;

@Data
public class CreateUserRequest {

    private String username;
    private String password;
    private String fistname;
    private String lastname;

}

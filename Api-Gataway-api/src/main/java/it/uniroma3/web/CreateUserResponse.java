package it.uniroma3.web;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateUserResponse {

    private Long id;
    private String username;
    private String password;
}

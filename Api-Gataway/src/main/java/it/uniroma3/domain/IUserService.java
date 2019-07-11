package it.uniroma3.domain;

public interface IUserService {

    User create(String username, String password, String firstname, String lastname);
    User findByUsername(String username);
}

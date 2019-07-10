package it.uniroma3.domain;

public interface IUserService {

    User create(String username, String password);
    User findByUsername(String username);
}

package it.uniroma3.domain;

import java.util.List;

public interface IConsumerService {
    List<Consumer> findAll();
    void save(Consumer consumer);
    void deleteById(Long id);
   Consumer findById(Long id);
    Consumer create (String FirstName, String LastName );
}

package it.uniroma3.domain;
import java.util.List;
import java.util.Optional;


public interface IConsumerService {
    List<Consumer> findAll();
    void deleteById(Long id);
    Consumer findById(Long id);
    Consumer create (String FirstName, String LastName );
    Consumer createWithId(Long id, String firstName, String lastName);
}

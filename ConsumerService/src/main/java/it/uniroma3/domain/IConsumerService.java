package it.uniroma3.domain;
import java.util.List;
import java.util.Optional;


public interface IConsumerService {
    List<Consumer> findAll();
    void save(Consumer consumer);
    void deleteById(Long id);
    Optional<Consumer> findById(Long id);
    Consumer create (String FirstName, String LastName );
}

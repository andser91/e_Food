package domain;

import java.util.List;
import java.util.Optional;

public interface IConsumerService {
    List<domain.Consumer> findAll();
    void save(domain.Consumer consumer);
    void deleteById(Long id);
    Optional<domain.Consumer> findById(Long id);
    domain.Consumer create (String FirstName, String LastName );
}

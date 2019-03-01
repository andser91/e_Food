package it.uniroma3.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class ConsumerService implements IConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Override
    public List<Consumer> findAll(){
        return (List<Consumer>)consumerRepository.findAll();
    }

    @Override
    public void save(Consumer restaurant) {
        this.consumerRepository.save(restaurant);
    }

    @Override
    public void deleteById(Long id) {
        this.consumerRepository.deleteById(id);
    }

    @Override
    public Consumer findById(Long id){

        return consumerRepository.findById(id).orElse(null);
    }

    @Override
    public Consumer create(String firstName, String lastName) {
        Consumer consumer = new Consumer(firstName, lastName);
        consumer = consumerRepository.save(consumer);
        return consumer;
    }
}

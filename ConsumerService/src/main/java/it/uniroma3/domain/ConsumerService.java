package it.uniroma3.domain;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ConsumerService implements domain.IConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Override
    public List<domain.Consumer> findAll(){
        return (List<domain.Consumer>)consumerRepository.findAll();
    }

    @Override
    public void save(domain.Consumer restaurant) {
        this.consumerRepository.save(restaurant);
    }

    @Override
    public void deleteById(Long id) {
        this.consumerRepository.deleteById(id);
    }

    @Override
    public Optional<domain.Consumer> findById(Long id){
        return this.consumerRepository.findById(id);
    }

    @Override
    public domain.Consumer create(String firstName, String lastName) {
        domain.Consumer consumer = new domain.Consumer(firstName, lastName);
        consumer = consumerRepository.save(consumer);
        return consumer;
    }
}

package it.uniroma3.domain;

import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.common.event.DomainEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ConsumerService implements IConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private DomainEventListener domainEvent;

    @Override
    public List<Consumer> findAll(){
        return (List<Consumer>)consumerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.consumerRepository.deleteById(id);
    }


    @Override
    public Consumer findById(Long consumerId){
        return consumerRepository.findById(consumerId).orElse(null);
    }


    @Override
    public Consumer create(String firstName, String lastName) {
        Consumer consumer = new Consumer(firstName, lastName);
        consumer = consumerRepository.save(consumer);
        return consumer;
    }


    //Validazione dell'ordine, ricevuto l'evento da Order controlla che il consumerId esista e re-invia un msg con la risposta
    public void validateOrder(Long consumerId){
        Consumer consumer = findById(consumerId);
        if(consumer == null){
            //creare evento ConsumerNonValido
        }
        else{
            //creare evento consumerValido
        }
    }
}

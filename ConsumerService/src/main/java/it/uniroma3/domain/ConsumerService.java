package it.uniroma3.domain;

import it.uniroma3.ConsumerServiceChannel;
import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.OrderConsumerInvalidatedEvent;
import it.uniroma3.event.OrderConsumerValidatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class ConsumerService implements IConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private DomainEventPublisher domainEventPublisher;

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

    @Override
    public Consumer createWithId(Long id, String firstName, String lastName) {
        Consumer consumer = new Consumer(id, firstName, lastName);
        consumer = consumerRepository.save(consumer);
        return consumer;
    }




    //Validazione dell'ordine, ricevuto l'evento da Order controlla che il consumerId esista e re-invia un msg con la risposta
    public void validateOrder(Long orderId, Long consumerId){
        Consumer consumer = findById(consumerId);
        if(consumer == null){
            //creare evento ConsumerNonValido
            OrderConsumerInvalidatedEvent invalidatedEvent = new OrderConsumerInvalidatedEvent(orderId, consumerId);
            System.out.println("### INVIATO EVENTO CONSUMER INVALID ###");
            domainEventPublisher.publish(invalidatedEvent, ConsumerServiceChannel.consumerServiceChannel);
        }
        else{
            //creare evento consumerValido
            OrderConsumerValidatedEvent validatedEvent = new OrderConsumerValidatedEvent(orderId, consumerId);
            System.out.println("### INVIATO EVENTO CONSUMER VALID ###");
            domainEventPublisher.publish(validatedEvent, ConsumerServiceChannel.consumerServiceChannel);

        }
    }
}

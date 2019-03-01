package it.uniroma3.web;

import it.uniroma3.CreateConsumerRequest;
import it.uniroma3.CreateConsumerResponse;
import it.uniroma3.GetConsumerResponse;
import it.uniroma3.GetConsumersResponse;
import it.uniroma3.domain.Consumer;
import it.uniroma3.domain.IConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/consumers")
public class ConsumerController {

    @Autowired
    private IConsumerService consumerService;

    /** Crea un nuovo Consumer **/
    @RequestMapping(path="/", method= RequestMethod.POST)
    public CreateConsumerResponse createConsumer(@RequestBody CreateConsumerRequest request) {
        Consumer consumer = consumerService.create(request.getFirstName(), request.getLastName());
        return makeCreateConsumerResponse(consumer);
    }

    private CreateConsumerResponse makeCreateConsumerResponse(Consumer consumer) {
        return new CreateConsumerResponse(consumer.getId());
    }

    /** Trova un consumer per id **/
    @RequestMapping(path="/{consumerId}", method=RequestMethod.GET)
    public ResponseEntity<GetConsumerResponse> getConsumer(@PathVariable Long consumerId) {
        Consumer consumer = consumerService.findById(consumerId);
        if (consumer!=null) {
            return new ResponseEntity<>(makeGetConsumerResponse(consumer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetConsumerResponse makeGetConsumerResponse(Consumer consumer) {
        return new GetConsumerResponse(consumer.getId(), consumer.getFirstName(), consumer.getLastName());
    }

    /** Trova tutti i consumatori. */
    @RequestMapping(path="/", method=RequestMethod.GET)
    public ResponseEntity<GetConsumersResponse> getConsumers() {
        List<Consumer> consumers = consumerService.findAll();
        if (consumers!=null) {
            return new ResponseEntity<>(makeGetConsumersResponse(consumers), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetConsumersResponse makeGetConsumersResponse(List<Consumer> consumers) {
        List<GetConsumerResponse> responses =
                consumers
                        .stream()
                        .map(c -> makeGetConsumerResponse(c))
                        .collect(Collectors.toList());
        return new GetConsumersResponse(responses);
    }

    /** Cancella un consumer per id **/
    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable Long id) {
        consumerService.deleteById(id);
    }

}

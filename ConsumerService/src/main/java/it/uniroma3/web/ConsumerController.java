package it.uniroma3.web;

import it.uniroma3.domain.Consumer;
import it.uniroma3.domain.ConsumerService;
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
    private ConsumerService consumerService;

    /** Trova tutti i consumers**/
    @GetMapping("/")
    public ResponseEntity<GetConsumersResponse> findAll(){
        List<Consumer>  consumers = consumerService.findAll();
        if(consumers!=null) {
            return new ResponseEntity<GetConsumersResponse>(makeGetConsumersResponse(consumers), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetConsumersResponse makeGetConsumersResponse(List<Consumer> consumers){
        List<GetConsumerResponse> responses = consumers.stream().map(consumer -> makeGetConsumerResponse(consumer)).collect(Collectors.toList());
        return new GetConsumersResponse(responses);
    }

    /** Crea un nuovo consumer**/
    @PostMapping("/")
    public CreateConsumerResponse newConsumer(@RequestBody CreateConsumerRequest request) {
        Consumer consumer = consumerService.create(request.getFirstName(), request.getLastName());
        return makeCreateConsumerResponse(consumer);
    }

    private CreateConsumerResponse makeCreateConsumerResponse(Consumer consumer){
        return new CreateConsumerResponse(consumer.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetConsumerResponse> findById(@PathVariable Long id ){
        Consumer consumer = consumerService.findById(id);
        if(consumer!=null){
            return new ResponseEntity<GetConsumerResponse>(makeGetConsumerResponse(consumer), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetConsumerResponse makeGetConsumerResponse(Consumer consumer){
        return new GetConsumerResponse(consumer.getId(), consumer.getFirstName(), consumer.getLastName());
    }

    @DeleteMapping("/{id}")
    public void deleteConsumer(@PathVariable Long id) {
        consumerService.deleteById(id);
    }
}

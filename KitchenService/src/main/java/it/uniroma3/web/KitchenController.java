package it.uniroma3.web;


import it.uniroma3.domain.IKitchenService;
import it.uniroma3.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/tickets")
public class KitchenController {

    @Autowired
    IKitchenService kitchenService;

    /** Trova tutti i tickets **/
    @GetMapping("/")
    public ResponseEntity<GetTicketsResponse> findAll(){
        List<Ticket> tickets = kitchenService.findAll();
        if (tickets != null) {
            return new ResponseEntity<GetTicketsResponse>(makeGetTicketsResponse(tickets), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetTicketsResponse makeGetTicketsResponse(List<Ticket> tickets){
        List<GetTicketResponse> responses = tickets.stream().map(ticket -> makeGetTicketResponse(ticket)).collect(Collectors.toList());
        return new GetTicketsResponse(responses);
    }

    /** Cancella un ticket per id **/
    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        kitchenService.deleteById(id);
    }

    /** Trova un ticket per id **/
    @GetMapping("/{id}")
    public ResponseEntity<GetTicketResponse> findById(@PathVariable Long id) {
        Ticket ticket = kitchenService.findById(id);
        if (ticket!=null) {
            return new ResponseEntity<>(makeGetTicketResponse(ticket), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private GetTicketResponse makeGetTicketResponse(Ticket ticket) {
        return new GetTicketResponse(ticket.getId(), ticket.getRestaurantId(), ticket.getState().toString());
    }

    /** Crea un nuovo ticket **/
    @PostMapping("/")
    public CreateTicketResponse newTicket(@RequestBody CreateTicketRequest request) {
        Ticket ticket = kitchenService.create(request.getRestaurantId());
        return makeCreateTicketResponse(ticket);

    }

    private CreateTicketResponse makeCreateTicketResponse(Ticket ticket) {
        return new CreateTicketResponse(ticket.getId(), ticket.getRestaurantId(), ticket.getState().toString());
    }

    /** Accetta un ticket e lo aggiorna**/
    @PostMapping("/{id}")
    public ResponseEntity<GetTicketResponse> acceptTicket(@PathVariable Long id){
        Ticket ticket = kitchenService.findById(id);
        if (ticket!=null) {
            kitchenService.acceptTicket(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}

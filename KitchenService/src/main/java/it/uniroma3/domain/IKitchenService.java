package it.uniroma3.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IKitchenService {
    List<Ticket> findAll();
    void deleteById(Long id);
    Ticket findById(Long id);
    Ticket create(Long restaurantId);
    Ticket acceptTicket(Long id);

}

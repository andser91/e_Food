package it.uniroma3.domain;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IOrderService {
    List<Order> findAll();
    void save(Order order);
    void deleteById(Long id);
    Order findById(Long id);
    Order create(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems);

}

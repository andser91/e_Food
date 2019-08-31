package it.uniroma3.domain;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public interface IOrderService {
    List<Order> findAll();
    void deleteById(Long id);
    Order findById(Long id);
    Order create(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems, double totalPrice);
    Order createAsincrona(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems, double totalPrice);


}

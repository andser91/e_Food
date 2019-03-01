package it.uniroma3.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order findById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        return order;
    }

    @Override
    public Order create(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems){
        Order order = Order.create(consumerId, restaurantId, orderLineItems);
        order = orderRepository.save(order);
        return order;
    }
}

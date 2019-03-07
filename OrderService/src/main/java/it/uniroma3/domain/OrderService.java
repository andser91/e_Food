package it.uniroma3.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ConsumerServiceAdapter consumerServiceAdapter;
    @Autowired
    private RestaurantServiceAdapter restaurantServiceAdapter;

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
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
    public Order create(Long restaurantId, Long consumerId,  List<OrderLineItem> orderLineItems){
        Order order = Order.create(restaurantId, consumerId,  orderLineItems);
        boolean consumerOk = consumerServiceAdapter.validateConsumer(order.getConsumerId());
        boolean restaurantOK = restaurantServiceAdapter.validateRestaurant(order.getRestaurantId());
        if (consumerOk && restaurantOK) {
            order.setOrderState(OrderState.APPROVED);
        }
        else if (restaurantOK) {
            order.setOrderState(OrderState.RESTAURANT_APPROVED);
        } else if (consumerOk) {
            order.setOrderState(OrderState.CONSUMER_APPROVED);
        } else {
            order.setOrderState(OrderState.INVALID);
        }
        order = orderRepository.save(order);
        return order;
    }
}

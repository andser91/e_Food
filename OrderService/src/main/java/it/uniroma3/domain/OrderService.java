package it.uniroma3.domain;

import it.uniroma3.OrderServiceChannel;
import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.LineItem;
import it.uniroma3.event.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ConsumerServiceAdapter consumerServiceAdapter;
    @Autowired
    private RestaurantServiceAdapter restaurantServiceAdapter;
    @Autowired
    private DomainEventPublisher domainEventPublisher;

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
        return orderRepository.findById(id).orElse(null);
    }

    /* Creazione di un nuovo ordine. */
    @Override
    public Order create(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems) {
        return createAsincrona(consumerId, restaurantId, orderLineItems);
        // return createSincrona(consumerId, restaurantId, orderLineItems);
    }

    private Order createAsincrona(Long consumerId, Long restaurantId, List<OrderLineItem> orderLineItems){
        //crea e salva l'ordine
        Order order = Order.create(consumerId, restaurantId,  orderLineItems);
        order = orderRepository.save(order);
        //pubblica un evento di creazione dell'ordine
        OrderCreatedEvent event = makeOrderCreatedEvent(order);
        domainEventPublisher.publish(event, OrderServiceChannel.orderServiceChannel);
        return order;
    }

    private OrderCreatedEvent makeOrderCreatedEvent(Order order){
        List<LineItem> lineItems = order.getOrderLineItems()
                .stream()
                .map(x -> new LineItem(x.getMenuItemId(), x.getQuantity()))
                .collect(Collectors.toList());
        return new OrderCreatedEvent(order.getId(),order.getConsumerId(),order.getRestaurantId(),lineItems);
    }


    private Order createSincrona(Long consumerId, Long restaurantId,  List<OrderLineItem> orderLineItems){
        Order order = Order.create(consumerId, restaurantId, orderLineItems);
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

    public Order confirmConsumer(Long orderId, Long consumerId) {

        /* TODO: in modo misterioso, cercando l'ordine usando l'operazione del servizio non funziona */
        // Order order = findById(orderId);
        /* TODO: funziona invece usando l'operazione del repository */
        /* TODO: potrebbe essere legato all'uso di @Transactional? */
        Order order = findById(orderId);
        if (order.getOrderState().equals(OrderState.PENDING)) {
            order.setOrderState(OrderState.CONSUMER_APPROVED);
            order = orderRepository.save(order);
        } else if (order.getOrderState().equals(OrderState.RESTAURANT_APPROVED)) {
            order.setOrderState(OrderState.APPROVED);
            order = orderRepository.save(order);
        }
        return order;
    }

    public Order confirmRestaurant(Long orderId, Long restaurantId) {

        /* TODO: stessa cosa di cui sopra, cercando l'ordine usando l'operazione del servizio non funziona */
        Order order = findById(orderId);
        if (order.getOrderState().equals(OrderState.PENDING)) {
            order.setOrderState(OrderState.RESTAURANT_APPROVED);
            order = orderRepository.save(order);
        } else if (order.getOrderState().equals(OrderState.CONSUMER_APPROVED)) {
            order.setOrderState(OrderState.APPROVED);
            order = orderRepository.save(order);
        }
        return order;
    }

    public Order invalidateConsumer(Long orderId, Long consumerId) {
        Order order = findById(orderId);
        order.setOrderState(OrderState.INVALID);
        order = orderRepository.save(order);
        return order;
    }


    public Order invalidateRestaurant(Long orderId, Long restaurantId) {
        Order order = findById(orderId);
        order.setOrderState(OrderState.INVALID);
        order = orderRepository.save(order);
        return order;
    }

}

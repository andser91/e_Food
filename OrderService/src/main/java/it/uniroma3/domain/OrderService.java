package it.uniroma3.domain;

import it.uniroma3.OrderServiceChannel;
import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.LineItem;
import it.uniroma3.event.OrderCreatedEvent;
import it.uniroma3.event.OrderDetails;
import it.uniroma3.sagas.CreateOrderSaga;
import it.uniroma3.sagas.CreateOrderSagaState;
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
    /*@Autowired
    private RestaurantServiceAdapter restaurantServiceAdapter;
    @Autowired
    private KitchenServiceAdapter kitchenServiceAdapter;*/
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
        Order order = Order.create(consumerId, restaurantId, orderLineItems);
        order = orderRepository.save(order);
        //pubblica un evento di creazione dell'ordine
        OrderCreatedEvent event = makeOrderCreatedEvent(order);
        domainEventPublisher.publish(event, OrderServiceChannel.orderServiceChannel);
        List<LineItem> lineItems = makeLineItem(order);
        OrderDetails orderDetails = new OrderDetails(lineItems, restaurantId, consumerId);
        CreateOrderSagaState data = new CreateOrderSagaState(order.getId(), orderDetails);
        return order;
    }
    private List<LineItem> makeLineItem(Order order){
        List<LineItem> lineItems = order.getOrderLineItems()
                .stream()
                .map(x -> new LineItem(x.getMenuItemId(), x.getQuantity()))
                .collect(Collectors.toList());
        return lineItems;
    }
    private OrderCreatedEvent makeOrderCreatedEvent(Order order){
        List<LineItem> lineItems = order.getOrderLineItems()
                .stream()
                .map(x -> new LineItem(x.getMenuItemId(), x.getQuantity()))
                .collect(Collectors.toList());
        return new OrderCreatedEvent(order.getId(),order.getConsumerId(),order.getRestaurantId(), order.getTicketId(), lineItems);
    }

    //TODO sistema con kitchenId
 /*   private Order createSincrona(Long consumerId, Long restaurantId, Long kitchenId,  List<OrderLineItem> orderLineItems){
        Order order = Order.create(consumerId, restaurantId, kitchenId, orderLineItems);
        boolean consumerOk = consumerServiceAdapter.validateConsumer(order.getConsumerId());
        boolean restaurantOK = restaurantServiceAdapter.validateRestaurant(order.getRestaurantId());
        if (consumerOk && restaurantOK) {
            order.setOrderState(OrderState.APPROVED);
        }
        else if (restaurantOK) {
            order.setOrderState(OrderState.KITCHEN_APPROVED);
        } else if (consumerOk) {
            order.setOrderState(OrderState.CONSUMER_APPROVED);
        } else {
            order.setOrderState(OrderState.INVALID);
        }
        order = orderRepository.save(order);
        return order;
    }
*/
    public Order confirmConsumer(Long orderId, Long consumerId) {

        /* TODO: in modo misterioso, cercando l'ordine usando l'operazione del servizio non funziona */
        // Order order = findById(orderId);
        /* TODO: funziona invece usando l'operazione del repository */
        /* TODO: potrebbe essere legato all'uso di @Transactional? */
        Order order = findById(orderId);
        if (order.getOrderState().equals(OrderState.PENDING) ) {
            order.setOrderState(OrderState.CONSUMER_APPROVED);
            order = orderRepository.save(order);
        } else if (order.getOrderState().equals(OrderState.KITCHEN_APPROVED)) {
            order.setOrderState(OrderState.APPROVED);
            order = orderRepository.save(order);
        } else if (order.getOrderState().equals(OrderState.TICKET_CREATED)){
            order.setOrderState(OrderState.TICKET_CREATED_AND_CONSUMER_APPROVED);
            order = orderRepository.save(order);
        }
        return order;
    }

    public Order confirmTicket(Long orderId, Long ticketId) {

        /* TODO: stessa cosa di cui sopra, cercando l'ordine usando l'operazione del servizio non funziona */
        Order order = findById(orderId);
        if (order.getOrderState().equals(OrderState.PENDING) || order.getOrderState().equals(OrderState.TICKET_CREATED) ) {
            order.setOrderState(OrderState.KITCHEN_APPROVED);
            order = orderRepository.save(order);
        } else if (order.getOrderState().equals(OrderState.CONSUMER_APPROVED) || order.getOrderState().equals(OrderState.TICKET_CREATED_AND_CONSUMER_APPROVED) ) {
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

    public Order invalidateTicket(Long orderId, Long ticketId) {
        Order order = findById(orderId);
        order.setOrderState(OrderState.INVALID);
        order = orderRepository.save(order);
        return order;
    }

    public Order attendingConfirmTicket(Long orderId, Long ticketId) {
        Order order = findById(orderId);
        order.setTicketId(ticketId);
        orderRepository.save(order);
        if(order.getOrderState().equals(OrderState.PENDING)) {
            order.setOrderState(OrderState.TICKET_CREATED);
            orderRepository.save(order);
        }else if(order.getOrderState().equals(OrderState.CONSUMER_APPROVED)) {
            order.setOrderState(OrderState.TICKET_CREATED_AND_CONSUMER_APPROVED);
            orderRepository.save(order);
        }
        return order;
    }
}

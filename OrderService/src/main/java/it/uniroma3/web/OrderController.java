package it.uniroma3.web;

import io.micrometer.core.instrument.MeterRegistry;
import it.uniroma3.domain.IOrderService;
import it.uniroma3.domain.Order;
import it.uniroma3.domain.OrderLineItem;
import it.uniroma3.domain.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final IOrderService orderService;

    private final MeterRegistry meterRegistry;


    /** Trova tutti gli ordini **/
    @GetMapping("/")
    public ResponseEntity<GetOrdersResponse> findAll(){
        List<Order> orders = orderService.findAll();
        if (orders != null) {
            return new ResponseEntity<>(makeGetOrdersResponse(orders), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetOrdersResponse makeGetOrdersResponse(List<Order> orders) {
        List<GetOrderResponse> responses = orders.stream().map(this::makeGetOrderResponse).collect(Collectors.toList());
        return new GetOrdersResponse(responses);
    }

    /** Crea un nuovo ordine **/
    @PostMapping("/")
    public CreateOrderResponse newOrder(@RequestHeader("version") String version, @RequestBody CreateOrderRequest request) {
    	log.info("   ---------  Version: {}   --------", version);
        List<OrderLineItem> orderLineItems = getOrderLineItems(request);
        Order order = orderService.create(request.getConsumerId(), request.getRestaurantId(), orderLineItems, request.getTotalPrice());
        meterRegistry.counter("total.cash","version", version).increment(request.getTotalPrice());
        meterRegistry.counter("order.count", "version",version).increment();
        return makeCreateOrderResponse(order);
    }

    private List<OrderLineItem> getOrderLineItems(CreateOrderRequest request) {
        return request.getLineItems()
                .stream()
                .map(x -> new OrderLineItem(x.getMenuItemId(), x.getQuantity()))
                .collect(Collectors.toList());
    }

    private CreateOrderResponse makeCreateOrderResponse(Order order) {
        return new CreateOrderResponse(order.getId(), order.getConsumerId(), order.getRestaurantId(), order.getOrderState().toString());
    }

    /** Trova un ordine per id **/
    @GetMapping("/{id}")
    public ResponseEntity<GetOrderResponse> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        if (order!=null) {
            return new ResponseEntity<>(makeGetOrderResponse(order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private GetOrderResponse makeGetOrderResponse(Order order) {
        List<LineItem> lineItems =
                order.getOrderLineItems()
                        .stream()
                        .map(x -> new LineItem(x.getMenuItemId(), x.getQuantity()))
                        .collect(Collectors.toList());
        return new GetOrderResponse(order.getId(), order.getConsumerId(), order.getRestaurantId(), order.getTicketId(), lineItems, order.getOrderState().toString(),order.getTotal());

    }


    /** Cancella un ordine per id **/
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}

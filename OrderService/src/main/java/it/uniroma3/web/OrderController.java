package it.uniroma3.web;

import it.uniroma3.domain.IOrderService;
import it.uniroma3.domain.Order;
import it.uniroma3.domain.OrderLineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/orders")
public class OrderController {
    @Autowired
    IOrderService orderService;

    //crea un nuovo ordine nel database
    @PostMapping("/")
    public CreateOrderResponse newOrder(@RequestBody CreateOrderRequest request) {
        List<OrderLineItem> orderLineItems = getOrderLineItems(request);
        Order order = orderService.create(request.getConsumerId(), request.getRestaurantId(), orderLineItems);
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

    //Single item
    //ricerca di un ordine per id
    @GetMapping("/{id}")
    ResponseEntity<GetOrderResponse> findById(@PathVariable Long id) {
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
        return new GetOrderResponse(order.getId(), order.getConsumerId(), order.getRestaurantId(), lineItems, order.getOrderState().toString());

    }


    //cancellazione di un ordine per id
    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}

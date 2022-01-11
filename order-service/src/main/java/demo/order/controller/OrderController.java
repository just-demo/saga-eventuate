package demo.order.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import demo.order.model.OrderCreateModel;
import demo.order.model.OrderModel;
import demo.order.service.OrderSagaService;
import demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderSagaService orderSagaService;
  private final OrderService orderService;

  @PostMapping
  public OrderModel createOrder(@RequestBody OrderCreateModel order) {
    return orderSagaService.createOrder(order);
  }

  @GetMapping("/{orderId}")
  public OrderModel getOrder(@PathVariable Long orderId) {
    return orderService.findOrder(orderId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }

  @GetMapping("/customer/{customerId}")
  public List<OrderModel> getCustomerOrders(@PathVariable Long customerId) {
    return orderService.findOrders(customerId);
  }
}

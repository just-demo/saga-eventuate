package demo.order.service;

import demo.order.model.OrderCreateModel;
import demo.order.model.OrderModel;

public interface OrderSagaService {

  OrderModel createOrder(OrderCreateModel order);
}

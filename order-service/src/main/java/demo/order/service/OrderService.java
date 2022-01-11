package demo.order.service;

import java.util.List;
import java.util.Optional;

import demo.order.api.common.RejectionReason;
import demo.order.model.OrderCreateModel;
import demo.order.model.OrderModel;

public interface OrderService {

  OrderModel createOrder(OrderCreateModel order);

  Optional<OrderModel> findOrder(Long orderId);

  List<OrderModel> findOrders(Long customerId);

  void approveOrder(Long orderId);

  void rejectOrder(Long orderId, RejectionReason rejectionReason);
}

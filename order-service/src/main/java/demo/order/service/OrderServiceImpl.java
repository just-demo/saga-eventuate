package demo.order.service;

import static demo.order.api.common.OrderState.APPROVED;
import static demo.order.api.common.OrderState.PENDING;
import static demo.order.api.common.OrderState.REJECTED;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import demo.order.api.common.RejectionReason;
import demo.order.entity.Order;
import demo.order.model.OrderCreateModel;
import demo.order.model.OrderModel;
import demo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public OrderModel createOrder(OrderCreateModel order) {
    return toModel(orderRepository.save(toEntity(order)));
  }

  @Override
  public Optional<OrderModel> findOrder(Long orderId) {
    return orderRepository.findById(orderId).map(this::toModel);
  }

  @Override
  public List<OrderModel> findOrders(Long customerId) {
    return orderRepository.findAllByCustomerId(customerId)
        .stream()
        .map(this::toModel)
        .collect(toList());
  }

  @Override
  public void approveOrder(Long orderId) {
    orderRepository.findById(orderId).get()
        .setState(APPROVED);
  }

  @Override
  public void rejectOrder(Long orderId, RejectionReason rejectionReason) {
    Order order = orderRepository.findById(orderId).get();
    order.setState(REJECTED);
    order.setRejectionReason(rejectionReason);
  }

  private OrderModel toModel(Order order) {
    return new OrderModel(order.getId(), order.getState(), order.getRejectionReason());
  }

  private Order toEntity(OrderCreateModel order) {
    return new Order(order.getCustomerId(), order.getOrderTotal(), PENDING);
  }
}

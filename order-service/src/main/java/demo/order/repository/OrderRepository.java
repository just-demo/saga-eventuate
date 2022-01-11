package demo.order.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import demo.order.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

  List<Order> findAllByCustomerId(Long customerId);
}

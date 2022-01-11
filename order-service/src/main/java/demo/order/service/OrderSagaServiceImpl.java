package demo.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.order.api.sagas.createorder.CreateOrderSagaData;
import demo.order.model.OrderCreateModel;
import demo.order.model.OrderModel;
import demo.order.saga.CreateOrderSaga;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderSagaServiceImpl implements OrderSagaService {

  private final OrderService orderService;
  private final SagaInstanceFactory sagaInstanceFactory;
  private final CreateOrderSaga createOrderSaga;

  @Override
  @Transactional
  public OrderModel createOrder(OrderCreateModel order) {
    CreateOrderSagaData data = new CreateOrderSagaData();
    data.setCustomerId(order.getCustomerId());
    data.setOrderTotal(order.getOrderTotal());
    sagaInstanceFactory.create(createOrderSaga, data);
    // TODO: is it always immediately available? try sleeping inside the saga
    return orderService.findOrder(data.getOrderId()).get();
  }

}

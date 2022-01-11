package demo.order.saga;

import static demo.order.api.common.RejectionReason.INSUFFICIENT_CREDIT;
import static demo.order.api.common.RejectionReason.UNKNOWN_CUSTOMER;
import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import org.springframework.stereotype.Component;

import demo.customer.api.commands.ReserveCreditCommand;
import demo.customer.api.replies.CustomerCreditLimitExceeded;
import demo.customer.api.replies.CustomerNotFound;
import demo.order.api.sagas.createorder.CreateOrderSagaData;
import demo.order.model.OrderCreateModel;
import demo.order.model.OrderModel;
import demo.order.service.OrderService;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

  private final OrderService orderService;
  private final SagaDefinition<CreateOrderSagaData> sagaDefinition =
      step()
          .invokeLocal(this::create)
          .withCompensation(this::reject)
          .step()
          .invokeParticipant(this::reserveCredit)
          .onReply(CustomerNotFound.class, this::handleCustomerNotFound)
          .onReply(CustomerCreditLimitExceeded.class, this::handleCustomerCreditLimitExceeded)
          .step()
          .invokeLocal(this::approve)
          .build();

  @Override
  public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
    return this.sagaDefinition;
  }

  private void handleCustomerNotFound(CreateOrderSagaData data, CustomerNotFound reply) {
    data.setRejectionReason(UNKNOWN_CUSTOMER);
  }

  private void handleCustomerCreditLimitExceeded(CreateOrderSagaData data, CustomerCreditLimitExceeded reply) {
    data.setRejectionReason(INSUFFICIENT_CREDIT);
  }

  private void create(CreateOrderSagaData data) {
    OrderModel order = orderService.createOrder(new OrderCreateModel(data.getCustomerId(), data.getOrderTotal()));
    data.setOrderId(order.getOrderId());
  }

  private CommandWithDestination reserveCredit(CreateOrderSagaData data) {
    return send(new ReserveCreditCommand(data.getCustomerId(), data.getOrderId(), data.getOrderTotal()))
        .to("customerService")
        .build();
  }

  private void approve(CreateOrderSagaData data) {
    orderService.approveOrder(data.getOrderId());
  }

  private void reject(CreateOrderSagaData data) {
    orderService.rejectOrder(data.getOrderId(), data.getRejectionReason());
  }
}

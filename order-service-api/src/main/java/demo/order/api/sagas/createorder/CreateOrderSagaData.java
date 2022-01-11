package demo.order.api.sagas.createorder;

import java.math.BigDecimal;

import demo.order.api.common.RejectionReason;
import lombok.Data;

@Data
public class CreateOrderSagaData {

  private Long orderId;
  private Long customerId;
  private BigDecimal orderTotal;
  private RejectionReason rejectionReason;
}

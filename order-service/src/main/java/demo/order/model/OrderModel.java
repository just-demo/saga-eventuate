package demo.order.model;

import demo.order.api.common.OrderState;
import demo.order.api.common.RejectionReason;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {

  private Long orderId;
  private OrderState orderState;
  private RejectionReason rejectionReason;
}

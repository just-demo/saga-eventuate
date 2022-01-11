package demo.order.entity;


import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import demo.order.api.common.OrderState;
import demo.order.api.common.RejectionReason;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private Long customerId;

  private BigDecimal orderTotal;

  @Enumerated(STRING)
  private OrderState state;


  @Enumerated(STRING)
  private RejectionReason rejectionReason;

  @Version
  private Long version;

  public Order(Long customerId, BigDecimal orderTotal, OrderState state) {
    this.customerId = customerId;
    this.orderTotal = orderTotal;
    this.state = state;
  }
}

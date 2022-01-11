package demo.order.model;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateModel {

  private Long customerId;
  private BigDecimal orderTotal;
}

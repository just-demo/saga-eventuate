package demo.customer.model;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

  private Long customerId;
  private String name;
  private BigDecimal creditLimit;
}

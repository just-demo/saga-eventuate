package demo.customer.api.commands;

import java.math.BigDecimal;

import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveCreditCommand implements Command {

  private long customerId;
  private Long orderId;
  private BigDecimal orderTotal;
}

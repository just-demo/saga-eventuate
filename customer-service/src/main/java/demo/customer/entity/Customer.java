package demo.customer.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String name;

  private BigDecimal creditLimit;

  @ElementCollection
  private Map<Long, BigDecimal> creditReservations = new HashMap<>();

  @Version
  private Long version;

  public Customer(String name, BigDecimal creditLimit) {
    this.name = name;
    this.creditLimit = creditLimit;
  }
}

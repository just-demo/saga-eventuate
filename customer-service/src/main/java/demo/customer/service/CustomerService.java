package demo.customer.service;

import java.math.BigDecimal;
import java.util.Optional;

import demo.customer.model.CustomerCreateModel;
import demo.customer.model.CustomerModel;

public interface CustomerService {

  CustomerModel createCustomer(CustomerCreateModel customerModel);

  Optional<CustomerModel> findCustomer(Long customerId);

  void reserveCredit(Long customerId, Long orderId, BigDecimal orderTotal);
}

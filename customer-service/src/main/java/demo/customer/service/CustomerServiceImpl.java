package demo.customer.service;


import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import demo.customer.entity.Customer;
import demo.customer.exception.CustomerCreditLimitExceededException;
import demo.customer.exception.CustomerNotFoundException;
import demo.customer.model.CustomerCreateModel;
import demo.customer.model.CustomerModel;
import demo.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Override
  @Transactional
  public CustomerModel createCustomer(CustomerCreateModel customerModel) {
    return toModel(customerRepository.save(toEntity(customerModel)));
  }

  @Override
  public Optional<CustomerModel> findCustomer(Long customerId) {
    return customerRepository.findById(customerId).map(CustomerServiceImpl::toModel);
  }

  @Override
  public void reserveCredit(Long customerId, Long orderId, BigDecimal orderTotal)
      throws CustomerCreditLimitExceededException {
    // TODO: does it persist the customer???
    Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    if (getCreditAvailable(customer).compareTo(orderTotal) < 0) {
      throw new CustomerCreditLimitExceededException();
    }
    customer.getCreditReservations().put(orderId, orderTotal);
  }

  private static BigDecimal getCreditAvailable(Customer customer) {
    BigDecimal creditUsed = sum(customer.getCreditReservations().values());
    return customer.getCreditLimit().subtract(creditUsed);
  }

  private static BigDecimal sum(Collection<BigDecimal> values) {
    return values.stream().reduce(ZERO, BigDecimal::add);
  }

  private static Customer toEntity(CustomerCreateModel customer) {
    return new Customer(customer.getName(), customer.getCreditLimit());
  }

  private static CustomerModel toModel(Customer customer) {
    return new CustomerModel(customer.getId(), customer.getName(), customer.getCreditLimit());
  }
}

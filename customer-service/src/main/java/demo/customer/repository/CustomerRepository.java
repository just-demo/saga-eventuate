package demo.customer.repository;

import org.springframework.data.repository.CrudRepository;

import demo.customer.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}

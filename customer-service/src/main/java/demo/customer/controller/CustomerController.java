package demo.customer.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import demo.customer.model.CustomerCreateModel;
import demo.customer.model.CustomerModel;
import demo.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping
  public CustomerModel createCustomer(@RequestBody CustomerCreateModel customer) {
    return customerService.createCustomer(customer);
  }

  @GetMapping("/{customerId}")
  public CustomerModel getCustomer(@PathVariable Long customerId) {
    return customerService.findCustomer(customerId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }
}

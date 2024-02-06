package com.rava.learning.demo.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public final List<Customer> customers = new ArrayList<>(
            List.of(
                    new Customer(
                            1L, "Ali", "ali@rava.com",
                            LocalDate.of(2000, APRIL, 20), 23
                    ),
                    new Customer(
                            2L, "Maryam", "maryam@rava.com",
                            LocalDate.of(1995, AUGUST, 18), 28
                    )
            )
    );

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer getCustomerByEmail(String email) {
        return this.customerRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("Customer with email " + email + " not found")
                );
    }

    @Transactional
    public void addCustomer(CustomerDto customerDto) {
        Customer customer = new Customer(
                customerDto.name(),
                customerDto.email(),
                customerDto.dob()
        );
        this.customerRepository.save(customer);
    }

    @Transactional
    public void editCustomer(Long customerId, CustomerDto customerDto) {
        Customer oldCustomer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new RuntimeException("Customer with id " + customerId + " not found")
                );
        oldCustomer.setName(customerDto.name());
        oldCustomer.setEmail(customerDto.email());
        oldCustomer.setDob(customerDto.dob());
        this.customerRepository.save(oldCustomer);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}

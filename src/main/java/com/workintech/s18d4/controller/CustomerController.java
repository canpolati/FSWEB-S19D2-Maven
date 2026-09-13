package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        List<CustomerResponse> list = customerService.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> find(@PathVariable long id) {
        Customer customer = customerService.find(id);
        return ResponseEntity.ok(toResponse(customer));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@RequestBody Customer customer) {
        Customer saved = customerService.save(customer);
        return ResponseEntity.ok(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable long id, @RequestBody Customer customer) {
        customer.setId(id);
        Customer updated = customerService.save(customer);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable long id) {
        Customer deleted = customerService.delete(id);
        return ResponseEntity.ok(toResponse(deleted));
    }

    private CustomerResponse toResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary());
    }
}
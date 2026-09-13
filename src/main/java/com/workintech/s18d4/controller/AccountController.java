package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> list = accountService.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> find(@PathVariable long id) {
        Account account = accountService.find(id);
        return ResponseEntity.ok(toResponse(account));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<AccountResponse> save(@PathVariable long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        account.setCustomer(customer);
        Account saved = accountService.save(account);
        return ResponseEntity.ok(toResponse(saved));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<AccountResponse> update(@PathVariable long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        account.setCustomer(customer);
        Account updated = accountService.save(account);
        return ResponseEntity.ok(toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountResponse> remove(@PathVariable long id) {
        Account account = accountService.find(id);
        accountService.delete(id);
        return ResponseEntity.ok(toResponse(account));
    }

    private AccountResponse toResponse(Account account) {
        if (account == null) {
            return null;
        }
        Long customerId = account.getCustomer() != null ? account.getCustomer().getId() : null;
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(), customerId);
    }
}
package com.workintech.s18d4.controller;

import com.workintech.s18d4.entity.Address;
import com.workintech.s18d4.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> find(@PathVariable long id) {
        return ResponseEntity.ok(addressService.find(id));
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address address) {
        return ResponseEntity.ok(addressService.save(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable long id, @RequestBody Address address) {
        address.setId(id);
        return ResponseEntity.ok(addressService.save(address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Address> delete(@PathVariable long id) {
        return ResponseEntity.ok(addressService.delete(id));
    }
}
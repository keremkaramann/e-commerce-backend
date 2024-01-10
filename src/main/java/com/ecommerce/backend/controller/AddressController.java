package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.AddressDto;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.user.ApplicationUser;
import com.ecommerce.backend.exceptions.ErrorException;
import com.ecommerce.backend.service.AddressService;
import com.ecommerce.backend.service.AuthenticationService;
import com.ecommerce.backend.util.AddressValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;


    @GetMapping("/")
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public Address find(@PathVariable Long id) {
        return addressService.findById(id);
    }


    @PostMapping("/")
    public Address save(@RequestBody Address address) {
        AddressValidation.validateAddress(address);
        return addressService.save(address);
    }

    @PutMapping("/{id}")
    public Address save(@RequestBody Address address, @PathVariable Long id) {
        Address foundAddress = addressService.findById(id);
        if (foundAddress != null) {
            address.setId(id);
            return addressService.save(address);
        }
        throw new ErrorException("Address with given id is not found " +id,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public Address remove(@PathVariable Long id) {
        AddressValidation.validateAddressId(id);
        return addressService.delete(id);
    }

}

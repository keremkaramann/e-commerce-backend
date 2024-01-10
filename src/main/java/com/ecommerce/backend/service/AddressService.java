package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAll();

    Address findById(Long id);

    Address save(Address address);

    Address delete(Long id);


}

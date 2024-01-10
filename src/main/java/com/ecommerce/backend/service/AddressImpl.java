package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.exceptions.ErrorException;
import com.ecommerce.backend.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Long id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if(optionalAddress.isPresent()){
            return optionalAddress.get();
        }
        throw new ErrorException("Address with giving id is not exist: " + id, HttpStatus.BAD_REQUEST );
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address delete(Long id) {
        Address address = findById(id);
        if(address!=null){
            addressRepository.delete(address);
            return address;
        }
        throw new ErrorException("Address with giving id is not exist: " + id, HttpStatus.BAD_REQUEST );
    }
}

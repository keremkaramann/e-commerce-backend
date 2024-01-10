package com.ecommerce.backend.dto;

import com.ecommerce.backend.entity.Address;

import java.util.List;

public record AddressListRecord (List<Address> address){
}

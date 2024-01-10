package com.ecommerce.backend.dto;

public record AddressDto(Long id, String title,String name,String surname,String phone,
                         String city,String district,String neighborhood,String address) {
}

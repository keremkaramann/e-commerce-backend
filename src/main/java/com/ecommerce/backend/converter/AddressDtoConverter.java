package com.ecommerce.backend.converter;

import com.ecommerce.backend.dto.AddressDto;
import com.ecommerce.backend.entity.Address;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class AddressDtoConverter {

    public List<AddressDto> convertToDtoList(List<Address> addressList) {
        return addressList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AddressDto convertToDto(Address address) {
        return new AddressDto(
                address.getId(),
                address.getTitle(),
                address.getName(),
                address.getSurname(),
                address.getPhone(),
                address.getCity(),
                address.getDistrict(),
                address.getNeighborhood(),
                address.getAddress()
        );
    }

    public void updateEntityWithDto(Address existingAddress, Address editedAddress) {
        existingAddress.setTitle(editedAddress.getTitle());
        existingAddress.setName(editedAddress.getName());
        existingAddress.setSurname(editedAddress.getSurname());
        existingAddress.setPhone(editedAddress.getPhone());
        existingAddress.setCity(editedAddress.getCity());
        existingAddress.setDistrict(editedAddress.getDistrict());
        existingAddress.setNeighborhood(editedAddress.getNeighborhood());
        existingAddress.setAddress(editedAddress.getAddress());
    }
}

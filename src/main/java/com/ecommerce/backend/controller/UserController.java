package com.ecommerce.backend.controller;

import com.ecommerce.backend.converter.AddressDtoConverter;
import com.ecommerce.backend.dto.AddressDto;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.user.ApplicationUser;
import com.ecommerce.backend.service.AddressService;
import com.ecommerce.backend.service.AuthenticationService;
import com.ecommerce.backend.util.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final AuthenticationService authenticationService;
    private final AddressDtoConverter addressDtoConverter;
    private final AddressService addressService;


    @PostMapping("/address/{userId}")
    public AddressDto addAddress(@RequestBody Address address, @PathVariable Long userId) {
        UserValidation.validateUserId(userId);
        ApplicationUser user = authenticationService.findById(userId);

        address.setApplicationUser(user);
        user.addAddress(address);

        authenticationService.save(user);

        return addressDtoConverter.convertToDto(address);
    }

    @GetMapping("/addresses/{userId}")
    public List<AddressDto> getAddressList(@PathVariable Long userId) {
        UserValidation.validateUserId(userId);
        ApplicationUser user = authenticationService.findUserWithAddressesById(userId);
        List<Address> addressList = user.getAddressList();

        return addressDtoConverter.convertToDtoList(addressList);
    }

    @PutMapping("/address/edit/{userId}/{addressId}")
    public AddressDto update(@RequestBody Address editedAddress,
                             @PathVariable Long userId, @PathVariable Long addressId) {

        UserValidation.validateUserId(userId);

        ApplicationUser userAddress = authenticationService.findUserWithAddressesById(userId);
        Address existingAddress = userAddress.getAddressList().stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst()
                .orElse(null);

        UserValidation.validateAddressExistence(existingAddress, addressId);

        addressDtoConverter.updateEntityWithDto(existingAddress, editedAddress);

        authenticationService.save(userAddress);

        return addressDtoConverter.convertToDto(existingAddress);
    }

    @DeleteMapping("/address/delete/{userId}/{addressId}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long userId, @PathVariable Long addressId) {

        UserValidation.validateUserId(userId);

        // Find the user with addresses
        ApplicationUser user = authenticationService.findUserWithAddressesById(userId);

        // Find the address to delete
        Address addressToDelete = user.getAddressList().stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst()
                .orElse(null);

        UserValidation.validateAddressExistence(addressToDelete, addressId);

        // Remove the address from the user's addressList
        user.getAddressList().remove(addressToDelete);

        // Delete the address directly from the database using the built-in delete method
        addressService.delete(addressId);

        return ResponseEntity.ok("Address with ID " + addressId + " deleted successfully");
    }

}

package com.ecommerce.backend.util;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.exceptions.ErrorException;
import org.springframework.http.HttpStatus;


public class UserValidation {

    private static final String ADDRESS_ID_NOT_EXIST = "Given user id does not exist or null!";

    public static void validateUserId(Long userId) throws ErrorException {
        if (userId == null) {
            throw new ErrorException(ADDRESS_ID_NOT_EXIST, HttpStatus.NOT_FOUND);
        }
    }

    public static void validateAddressExistence(Address existingAddress, Long addressId) throws ErrorException {
        if (existingAddress == null) {
            throw new ErrorException("Address not found with this id: " + addressId, HttpStatus.NOT_FOUND);
        }
    }

}

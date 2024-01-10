package com.ecommerce.backend.util;

import com.ecommerce.backend.dto.LoginDto;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.exceptions.ErrorException;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;

public class AddressValidation {
    private static final String ADDRESS_ID = "Given user id cannot be null or blank!";


    public static void validateAddressId(Long id) throws ErrorException {
        if (id == null || id.toString().isEmpty()) {
            throw new ErrorException(ADDRESS_ID, HttpStatus.NOT_FOUND);
        }
    }

    public static void validateNotBlankOrNullOrThrow(String value, String errorMessage) throws ErrorException {
        if (StringUtils.isBlank(value)) {
            throw new ErrorException(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
    public static void validateAddress(Address address) throws ErrorException {
        validateNotBlankOrNullOrThrow(address.getTitle(), "Address title cannot be null or blank!");
        validateNotBlankOrNullOrThrow(address.getName(), "Address name cannot be null or blank!");
        validateNotBlankOrNullOrThrow(address.getSurname(), "Address surname cannot be null or blank!");
        validateNotBlankOrNullOrThrow(address.getPhone(), "Address phone cannot be null or blank!");
        validateNotBlankOrNullOrThrow(address.getCity(), "Address city cannot be null or blank!");
        validateNotBlankOrNullOrThrow(address.getDistrict(), "Address district cannot be null or blank!");
        validateNotBlankOrNullOrThrow(address.getNeighborhood(), "Address neighborhood cannot be null or blank!");
        validateNotBlankOrNullOrThrow(address.getAddress(), "Address address cannot be null or blank!");
    }

}

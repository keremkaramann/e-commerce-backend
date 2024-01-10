package com.ecommerce.backend.util;

import com.ecommerce.backend.dto.LoginDto;
import com.ecommerce.backend.entity.Products;
import com.ecommerce.backend.exceptions.ErrorException;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;

public class ProductValidation {

    private static final String USER_IS_BLANK_OR_NULL = "Product id cannot be null or blank";
    private static final String PRODUCT_NAME_LENGTH = "Product name must be between 3 and 50 characters!";
    private static final String PRODUCT_DESCRIPTION_LENGTH = "Product description must be between 10 and 500 characters!";
    private static final String PRODUCT_PRICE_INVALID = "Product price must be a positive number!";
    private static final String PRODUCT_IMAGE_URL_INVALID = "Product image URL is invalid!";
    private static final String PRODUCT_RATING_INVALID = "Product rating is invalid!";
    private static final String PRODUCT_STOCK_INVALID = "Product stock is invalid!";

    public static void validateProductId(Long id) throws ErrorException {
        if (id == null) {
            throw new ErrorException(USER_IS_BLANK_OR_NULL, HttpStatus.NOT_FOUND);
        }
    }

    public static void validateAddedProducts(Products product) throws ErrorException {
        if (StringUtils.isBlank(product.getName()) || product.getName().length() < 3 || product.getName().length() > 100) {
            throw new ErrorException(PRODUCT_NAME_LENGTH, HttpStatus.BAD_REQUEST);
        }

        if (StringUtils.isBlank(product.getDescription()) || product.getDescription().length() < 5 || product.getDescription().length() > 150) {
            throw new ErrorException(PRODUCT_DESCRIPTION_LENGTH, HttpStatus.BAD_REQUEST);
        }

        if (product.getPrice() <= 0) {
            throw new ErrorException(PRODUCT_PRICE_INVALID, HttpStatus.BAD_REQUEST);
        }

        // Validate image URL
        if (StringUtils.isBlank(product.getImageUrl())) {
            throw new ErrorException(PRODUCT_IMAGE_URL_INVALID, HttpStatus.BAD_REQUEST);
        }

        // Validate rating
        if (product.getRating() < 0 || product.getRating() > 5) {
            throw new ErrorException(PRODUCT_RATING_INVALID, HttpStatus.BAD_REQUEST);
        }

        // Validate stock
        if (product.getStock() < 0) {
            throw new ErrorException(PRODUCT_STOCK_INVALID, HttpStatus.BAD_REQUEST);
        }
    }
}

package com.ecommerce.backend.util;

import com.ecommerce.backend.dto.LoginDto;
import com.ecommerce.backend.exceptions.ErrorException;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;

public class LoginValidation {

    private static final String USER_LENGHT = "Login email or password must be between 7 and 50 chars!";
    private static final String USER_IS_BLANK_OR_NULL = "USER email or password cannot be null or blank";


    public static void validate(LoginDto loginDto) throws ErrorException {
        if (loginDto.email().length() < 7 || loginDto.password().length() > 50) {
            throw new ErrorException(USER_LENGHT, HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(loginDto.email()) || StringUtils.isBlank(loginDto.password())) {
            throw new ErrorException(USER_IS_BLANK_OR_NULL, HttpStatus.BAD_REQUEST);
        }

    }
}

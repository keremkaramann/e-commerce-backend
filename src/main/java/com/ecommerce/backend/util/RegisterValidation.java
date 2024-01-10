package com.ecommerce.backend.util;

import com.ecommerce.backend.dto.RegisterUserRecord;
import com.ecommerce.backend.exceptions.ErrorException;
import com.ecommerce.backend.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class RegisterValidation {

    private static final String USER_LENGHT = "User name or email must be between 3 and 45 chars!";
    private static final String USER_PASSWORD = "USER password cannot be null or blank";
    private static final String USER_IS_BLANK_OR_NULL = "USER name or email cannot be null or blank";
 


    public static void validate(RegisterUserRecord registerUser) throws ErrorException {

        if (registerUser.fullName().length() < 3 || registerUser.fullName().length() > 45
                || registerUser.email().length() < 3 || registerUser.email().length() > 45) {
            throw new ErrorException(USER_LENGHT, HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(registerUser.password())) {
            throw new ErrorException(USER_PASSWORD, HttpStatus.BAD_REQUEST);
        }
        if (StringUtils.isBlank(registerUser.fullName()) || StringUtils.isBlank(registerUser.email())) {
            throw new ErrorException(USER_IS_BLANK_OR_NULL, HttpStatus.BAD_REQUEST);
        }

    }
}

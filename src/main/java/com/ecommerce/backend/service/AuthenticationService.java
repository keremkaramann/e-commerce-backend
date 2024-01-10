package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.user.ApplicationUser;

public interface AuthenticationService {
    ApplicationUser findById(Long id);

    ApplicationUser save(ApplicationUser applicationUser);

    ApplicationUser findUserWithAddressesById(Long userId);

}

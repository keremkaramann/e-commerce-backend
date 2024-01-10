package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.role.Role;
import com.ecommerce.backend.entity.user.ApplicationUser;
import com.ecommerce.backend.exceptions.ErrorException;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.util.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository,
                                     RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApplicationUser findById(Long id) {
        Optional<ApplicationUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ErrorException("User with given id is not found: " + id, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ApplicationUser save(ApplicationUser applicationUser) {
        return userRepository.save(applicationUser);
    }

    public ApplicationUser register(String fullName, String email, String password, String selectedRoles) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new RuntimeException("User with given email is already exist: " + email);
        }
        String encodedPassword = passwordEncoder.encode(password);
        Role defaultRole = roleRepository.findByAuthority("customer").
                orElseThrow(() -> new RuntimeException("Default role not found"));

        Role userRole;

        if (selectedRoles != null) {
            userRole = roleRepository.findByAuthority(selectedRoles.toLowerCase()).orElse(defaultRole);
        } else {
            userRole = defaultRole;
        }

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        ApplicationUser user = new ApplicationUser();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setAuthorities(roles);

        return userRepository.save(user);
    }

    public ApplicationUser login(String email, String password) {

        Optional<ApplicationUser> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            ApplicationUser applicationUser = optionalUser.get();
            boolean isSamePassword = passwordEncoder.matches(password, applicationUser.getPassword());
            if (isSamePassword) {
                return applicationUser;
            }
            throw new BadCredentialsException("Invalid Credentials: " + email);
        }
        throw new BadCredentialsException("Invalid Credentials: " + email);
    }

    @Override
    public ApplicationUser findUserWithAddressesById(Long userId) {
        Optional<ApplicationUser> userWithAddresses = userRepository.findUserWithAddressesById(userId);

        if (userWithAddresses.isPresent()) {
            return userWithAddresses.get();
        } else {
            throw new ErrorException("User with given id is not found" + userId, HttpStatus.BAD_REQUEST);
        }
    }
}

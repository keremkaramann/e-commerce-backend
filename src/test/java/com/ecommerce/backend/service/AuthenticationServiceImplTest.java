package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.user.ApplicationUser;

import com.ecommerce.backend.exceptions.ErrorException;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)
class AuthenticationServiceImplTest {

    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        authenticationService = new AuthenticationServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void findById() {
        ApplicationUser user = new ApplicationUser();
        user.setId(345L);
        user.setFullName("Joe yılmaz");
        user.setEmail("joekk@gm.com");
        user.setPassword("123456");

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        ApplicationUser result = authenticationService.findById(user.getId());

        verify(userRepository).findById(user.getId());

        assertEquals(user, result);
    }

    @Test
    void findByIdFail() {
        long notExistId = 999L;

        given(userRepository.findById(notExistId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.findById(notExistId))
                .isInstanceOf(ErrorException.class)
                .hasMessage("User with given id is not found: 999");
        verify(userRepository).findById(notExistId);
    }

  /*  @Test
    void save() {
        ApplicationUser user = new ApplicationUser();
        user.setId(566L);
        user.setFullName("Joe yılmaz");
        user.setEmail("joekk@gm.com");
        user.setPassword("123456");

        //stubbing
        given(userRepository.findById(566L)).willReturn(Optional.empty());
        authenticationService.save(user);
        verify(userRepository, times(1)).save(user);
    }*/

    @Test
    void saveFail() {
        ApplicationUser user = new ApplicationUser();
        user.setFullName("Joe yılmaz");
        user.setEmail("joekk@gm.com");
        user.setPassword("123456");

        //stubbing
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        assertThatThrownBy(() -> authenticationService.save(user))
                .isInstanceOf(ErrorException.class)
                .hasMessage("User with given id already exist!!: " + user.getId());

        verify(userRepository, never()).save(user);
    }

    @Test
    void register() {
    }

    @Test
    void login() {
    }

    @Test
    void findUserWithAddressesById() {
    }

}
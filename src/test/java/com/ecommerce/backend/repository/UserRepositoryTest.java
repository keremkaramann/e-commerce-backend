package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.user.ApplicationUser;
import com.ecommerce.backend.service.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
class UserRepositoryTest {
    private UserRepository userRepository;


    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private ApplicationUser user;
    private Address address;

    @BeforeEach
    void setUp() {
        Optional<ApplicationUser> optionalUser = userRepository.findUserByEmail("king.doe@example.com");

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new ApplicationUser();
            user.setFullName("King Doe");
            user.setEmail("king.doe@example.com");
            user.setPassword("password123");
            user = userRepository.save(user);
        }

        address = new Address();
        address.setTitle("Home");
        address.setName("King");
        address.setSurname("Doe");
        address.setPhone("1234567890");
        address.setCity("Cityz");
        address.setDistrict("Districtz");
        address.setNeighborhood("Neighborhoodz");
        address.setAddress("1234 Street");
        address.setApplicationUser(user);


        user.addAddress(address);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        if (user != null && user.getId() != null) {
            userRepository.deleteById(user.getId());
        }
    }

    @DisplayName("Can find user by user email")
    @Test
    void findUserByEmail() {
        Optional<ApplicationUser> optionalUser = userRepository.findUserByEmail(user.getEmail());
        assertNotNull(optionalUser);
        assertTrue(optionalUser.isPresent());

        ApplicationUser foundUser = optionalUser.get();
        assertEquals("King Doe", foundUser.getFullName());
        assertEquals("king.doe@example.com", foundUser.getEmail());
        assertEquals("password123", foundUser.getPassword());
    }

    @DisplayName("Cannot find user by user email")
    @Test
    void findUserByEmailFail() {
        Optional<ApplicationUser> optionalUser = userRepository.findUserByEmail("harrypotter@gm.com");
        assertNotNull(optionalUser);
        assertTrue(optionalUser.isEmpty());
        assertNull(optionalUser.orElse(null));
    }

    @DisplayName("Can find address by user id")
    @Test
    void findUserWithAddressesById() {
        Optional<ApplicationUser> addressOptional1 = userRepository.findUserWithAddressesById(user.getId());
        assertNotNull(addressOptional1);
        assertTrue(addressOptional1.isPresent());

        ApplicationUser foundUser = addressOptional1.get();

        assertEquals("King Doe", foundUser.getFullName());
        assertEquals("king.doe@example.com", foundUser.getEmail());
    }

    @DisplayName("Cannot find address by user id")
    @Test
    void findUserWithAddressesByIdFail() {
        Optional<ApplicationUser> addressOptional1 = userRepository.findUserWithAddressesById(500L);
        assertNotNull(addressOptional1);
        assertTrue(addressOptional1.isEmpty());

    }
}
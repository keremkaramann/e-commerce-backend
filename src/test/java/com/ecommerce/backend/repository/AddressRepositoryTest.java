package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.user.ApplicationUser;
import com.ecommerce.backend.service.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestConfig.class)
class AddressRepositoryTest {
    private AddressRepository addressRepository;
    private UserRepository userRepository;

    @Autowired
    public AddressRepositoryTest(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    private ApplicationUser user;
    private Address address;

    @BeforeEach
    void setUp() {
        Optional<ApplicationUser> optionalUser = userRepository.findUserByEmail("john.doe@example.com");

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new ApplicationUser();
            user.setFullName("John Doe");
            user.setEmail("john.doe@example.com");
            user.setPassword("password");
            user = userRepository.save(user);
        }

        address = new Address();
        address.setTitle("Home");
        address.setName("John");
        address.setSurname("Doe");
        address.setPhone("1234567890");
        address.setCity("City");
        address.setDistrict("District");
        address.setNeighborhood("Neighborhood");
        address.setAddress("123 Street");
        address.setApplicationUser(user);


        user.addAddress(address);
        userRepository.save(user);
    }

    @DisplayName("Can find address list of user by user id")
    @Test
    void findAddressByUser() {
        List<Address> foundAddresses = addressRepository.findAddressByUser(user.getId());
        assertNotNull(foundAddresses);
        assertEquals("John", address.getName());
        assertEquals("Doe", address.getSurname());
        assertEquals("Home", address.getTitle());
        assertEquals("City", address.getCity());
        assertEquals("1234567890", address.getPhone());
        assertEquals("123 Street", address.getAddress());
        assertEquals("District", address.getDistrict());
        assertEquals("Neighborhood", address.getNeighborhood());
    }
    @DisplayName("Cannot find address list of user by user id")
    @Test
    void findAddressByUserFail() {
        List<Address> foundAddresses = addressRepository.findAddressByUser(233L);

        assertTrue(foundAddresses.isEmpty(), "Expected the list to be empty");

    }
}
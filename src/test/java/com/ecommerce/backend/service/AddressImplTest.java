package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.exceptions.ErrorException;
import com.ecommerce.backend.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class AddressImplTest {

    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        addressService = new AddressImpl(addressRepository);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        Long addressId = 134L;
        Address testAddress = new Address();
        testAddress.setId(addressId);
        testAddress.setTitle("Home");
        testAddress.setName("joe");
        testAddress.setSurname("surname");
        testAddress.setPhone("11111111111");
        testAddress.setCity("testcity");
        testAddress.setDistrict("test dist");
        testAddress.setNeighborhood("tunalı");
        testAddress.setAddress("address test");

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(testAddress));

        Address result =addressService.findById(addressId);

        assertNotNull(result);
        assertEquals(addressId,result.getId());
        assertEquals("Home",testAddress.getTitle());
        assertEquals("tunalı",testAddress.getNeighborhood());
        assertEquals("joe",testAddress.getName());
        assertEquals("testcity",testAddress.getCity());
    }

    @Test
    void findByIdFail() {
        Long notFoundId=452L;

        when(addressRepository.findById(notFoundId)).thenReturn(Optional.empty());
        ErrorException exception = assertThrows(ErrorException.class, () -> addressService.findById(notFoundId));
        assertEquals("Address with giving id is not exist: " + notFoundId, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }
}
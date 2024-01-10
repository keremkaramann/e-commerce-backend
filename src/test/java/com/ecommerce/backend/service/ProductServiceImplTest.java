package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Products;
import com.ecommerce.backend.exceptions.ErrorException;
import com.ecommerce.backend.repository.ProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@SpringBootTest
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private ProductService productService;
    @Mock
    private ProductsRepository productsRepository;


    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productsRepository);
    }

    @Test
    @WithMockUser(authorities = "admin")
    void save() {
        Products testProduct = new Products();
        testProduct.setName("mouse");
        testProduct.setDescription("kelepir mouse");
        testProduct.setPrice(12.44);
        testProduct.setStock(3L);
        testProduct.setRating(3.44);
        testProduct.setImageUrl("https://www.teknostore.com/image/cache/data/resimler/logitech-g402-hyperion-fury-oyuncu-mouse-1887-682x682.jpg");

        productService.save(testProduct);
        verify(productsRepository).save(testProduct);
    }

    @Test
    @WithMockUser(authorities = "admin")
    void saveFail() {
        Products testProduct = new Products();
        testProduct.setName("mouse");
        testProduct.setDescription("kelepir mouse");
        testProduct.setPrice(12.44);
        testProduct.setStock(3L);
        testProduct.setRating(3.44);
        testProduct.setImageUrl("");

        ErrorException exception = assertThrows(ErrorException.class, () -> productService.save(testProduct));

        assertEquals("Product image URL is invalid!", exception.getMessage());

        verify(productsRepository, never()).save(testProduct);
    }

    @Test
    void saveAll() {
    }

    @Test
    void find() {
    }

    @Test
    void delete() {
    }


}
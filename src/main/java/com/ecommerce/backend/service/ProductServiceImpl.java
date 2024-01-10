package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Products;
import com.ecommerce.backend.exceptions.ErrorException;
import com.ecommerce.backend.repository.ProductsRepository;
import com.ecommerce.backend.util.ProductValidation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productsRepository;

    @CacheEvict(value = "productList", allEntries = true)
    @Override
    public Products save(Products products) {
        ProductValidation.validateAddedProducts(products);
        return productsRepository.save(products);
    }

    @Override
    public List<Products> saveAll(List<Products> products) {
        return productsRepository.saveAll(products);
    }

    @Override
    @Cacheable("productList")
    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Products find(Long id) {
        Optional<Products> optionalProduct = productsRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new ErrorException("Product with given id is not found" + id, HttpStatus.BAD_REQUEST);
    }

    @Override
    @CacheEvict(value = "productList", allEntries = true)
    public Products delete(Long id) {
       Products product = find(id);
        if (product != null) {
            productsRepository.delete(product);
            return product;
        }
        throw new ErrorException("Product with given id is not found" + id, HttpStatus.BAD_REQUEST);
    }
}

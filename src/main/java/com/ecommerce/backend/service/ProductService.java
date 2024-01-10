package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.Products;

import java.util.List;

public interface ProductService {

    Products save(Products products);
    List<Products> saveAll(List<Products> products);
    List<Products> findAll();
    Products find(Long id);
    Products delete(Long id);
}

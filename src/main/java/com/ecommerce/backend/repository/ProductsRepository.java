package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products,Long> {
}

package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    @Query("SELECT u FROM ApplicationUser u WHERE u.email=:email")
    Optional<ApplicationUser> findUserByEmail(String email);

    @Query("SELECT u FROM ApplicationUser u LEFT JOIN FETCH u.addressList WHERE u.id = :userId")
    Optional<ApplicationUser> findUserWithAddressesById(Long userId);

}

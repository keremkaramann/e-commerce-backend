package com.ecommerce.backend.entity;

import com.ecommerce.backend.entity.user.ApplicationUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "address", schema = "ecommerce")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "title")
    private String title;

    @NotNull
    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank
    @Column(name = "surname")
    private String surname;

    @NotNull
    @NotBlank
    @Column(name = "phone")
    private String phone;

    @NotNull
    @NotBlank
    @Column(name = "city")
    private String city;

    @NotNull
    @NotBlank
    @Column(name = "district")
    private String district;

    @NotNull
    @NotBlank
    @Column(name = "neighborhood")
    private String neighborhood;

    @NotNull
    @NotBlank
    @Column(name = "address")
    private String address;

    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

}

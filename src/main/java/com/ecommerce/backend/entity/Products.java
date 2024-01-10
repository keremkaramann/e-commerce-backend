package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "products", schema = "ecommerce")
public class Products {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank
    @NotNull
    private String name;

    @Column(name = "description")

    @NotNull
    private String description;

    @Column(name = "price")

    @NotNull
    private Double price;

    @Column(name = "stock")
    @NotNull
    private Long stock;

    @Column(name = "rating")

    @NotNull
    private Double rating;

    @Column(name = "image_url")
    @NotBlank
    @NotNull
    private String imageUrl;

}

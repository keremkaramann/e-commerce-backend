package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Products;
import com.ecommerce.backend.service.ProductService;
import com.ecommerce.backend.util.ProductValidation;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {


    private final ProductService productService;
    private final RestTemplateBuilder restTemplateBuilder;

    private static final String GET_ALL_PRODUCTS = "https://workintech-fe-ecommerce.onrender.com/products";


    @PostMapping("/save")
    public ResponseEntity<Products> save(@RequestBody Products product) {
        ProductValidation.validateAddedProducts(product);
        return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
    }

    @PostMapping("/all")
    public String saveAll() {
        //responses.getBody().get("products")
        //responses.getBody().get("products").get(0).get("name")
        //responses.getBody().get("products").get(0).get("images").get(0).get("url")
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<JsonNode> responses = restTemplate.getForEntity(GET_ALL_PRODUCTS, JsonNode.class);

        List<Products> productList = new ArrayList<>();
        for (JsonNode node : responses.getBody().get("products")) {
            Products product = new Products();
            product.setName(node.get("name").asText());
            product.setDescription(node.get("description").asText());
            product.setPrice(node.get("price").asDouble());
            product.setStock(node.get("stock").asLong());
            product.setRating(node.get("rating").asDouble());
            product.setImageUrl(node.get("images").get(0).get("url").asText());
            productList.add(product);
        }
        productService.saveAll(productList);
        return "Completed";
    }

    @GetMapping
    public ResponseEntity<List<Products>> findAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public Products delete(@PathVariable Long id ){
        ProductValidation.validateProductId(id);
        return productService.delete(id);
    }
}

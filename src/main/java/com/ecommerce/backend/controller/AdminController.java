package com.ecommerce.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/")
    public String admin(){
        return "This is admin area";
    }
}

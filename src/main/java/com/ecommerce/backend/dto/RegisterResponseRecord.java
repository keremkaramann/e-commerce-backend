package com.ecommerce.backend.dto;

import com.ecommerce.backend.entity.role.Role;

import java.util.Set;

public record RegisterResponseRecord(String fullName, String email, Set<Role> role) {
}

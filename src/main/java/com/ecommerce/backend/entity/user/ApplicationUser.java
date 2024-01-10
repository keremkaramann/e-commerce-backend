package com.ecommerce.backend.entity.user;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "user", schema = "ecommerce")
public class ApplicationUser implements UserDetails {

    @Id
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 45, message = ("Name size must be between 2 and 45"))
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 80, message = ("Password size must be between 2 and 80"))
    @Column(name = "email")
    private String email;

    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "role_junction", schema = "ecommerce",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> authorities = new HashSet<>();

    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Address> addressList;

    public void addAddress(Address address) {
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        addressList.add(address);
    }

    public Set<Role> getRoles() {
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

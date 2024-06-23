package com.darfik.cloudstorage.domain.user.model;

import com.darfik.cloudstorage.domain.user.dto.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class AppUser {

    @Id
    @SequenceGenerator(
            name = "app_user_sequence",
            sequenceName = "app_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_sequence"
    )
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @ElementCollection
    private Set<Role> roles;

    public AppUser(String email, String password, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

}

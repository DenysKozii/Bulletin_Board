package com.denyskozii.bulletinboard.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 20,
            message = "Length of first name should be between 2 and 20")
    @Column(name = "firstName")
    private String firstName;

    @NonNull
    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 20,
            message = "Length of last name should be between 2 and 20")
    @Column(name = "lastName")
    private String lastName;

    @NonNull
    @NotBlank(message = "Must not be empty")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    @Column(name = "email",unique = true)
    private String email;

    @NonNull
    @NotBlank(message = "Must not be empty")
    @Size(min = 1, message = "Password is not strong enough")
    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Transient
    private Date expirationDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Bulletin> bulletins = new ArrayList<>();

    private boolean active;

}

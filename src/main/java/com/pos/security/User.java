package com.pos.security;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * Created by rrampall on 14/03/18.
 */
@Entity
@Table(name = "user_accounts")
@PasswordMatches
public class User {


    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Getter @Setter
    private String firstName;

    @NotNull
    @NotEmpty
    @Getter @Setter
    private String lastName;

    @NotNull
    @NotEmpty
    @Getter @Setter
    @Column(length = 60)
    private String password;


    @NotNull
    @NotEmpty
    @Getter @Setter
    private String email;

    @Getter @Setter
    private boolean enabled;


    @Getter @Setter
    private String secret;

    @NotNull
    @NotEmpty
    @Getter @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Collection<Role> roles;

}

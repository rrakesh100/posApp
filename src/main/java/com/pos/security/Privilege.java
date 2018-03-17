package com.pos.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by rrampall on 15/03/18.
 */
@Entity
@Table(name = "privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @ManyToMany(mappedBy = "privileges")
    @Getter @Setter
    private Collection<Role> roles;

}

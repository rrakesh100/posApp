package com.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by rrampall on 26/01/18.
 */
@Entity
@Table(name="employees")
public class Employee {

    @Getter
    @Setter
    @Id
    private long id;

    @Getter @Setter
    @Column(name="username")
    private String userName;

    @Getter @Setter
    @Column(name="mobile_number")
    private String mobileNumber;

    @Getter @Setter
    @Column(name="password")
    private String password;

    @Getter @Setter
    @Column(name="name")
    private String name;

    @Getter @Setter
    @Column(name="email")
    private String email;

    @Getter @Setter
    @Column(name="address")
    private String address;

    @Getter @Setter
    @Column(name="date_of_birth")
    private String dob;


    @Getter @Setter
    @Column(name="deleted")
    private boolean deleted;

}

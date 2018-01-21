package com.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rrampall on 21/01/18.
 */
@Entity
@Table(name="customers")
public class Customer {


    @Getter
    @Setter
    @Column(name="mobile_number")
    @Id
    private Long mobileNumber;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    @Column(name="company_name")
    private String  companyName;


//    @Column(name="created_date")
//    @Getter
//    @Setter
//    private Date date;

    @Column(name="date_of_birth")
    @Getter
    @Setter
    private Date dob;

    @Getter
    @Setter
    private boolean deleted;




}

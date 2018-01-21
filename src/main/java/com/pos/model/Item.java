package com.pos.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rrampall on 19/12/17.
 */
@ToString
@Entity
@Table(name="items")
public class Item {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Id
    @Getter
    @Setter
    private String uid;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private String  sku;
    @Column(name="created_date")
    @Getter
    @Setter
    private Date date;


}

package com.pos.model;

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


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private @lombok.Getter @lombok.Setter Long id;
    private @lombok.Getter @lombok.Setter String  uid;
    private @lombok.Getter @lombok.Setter String name;
    private @lombok.Getter @lombok.Setter String description;
    @Column(name="created_date")
    private @lombok.Getter @lombok.Setter
    Date date;


}

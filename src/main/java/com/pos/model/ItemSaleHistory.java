package com.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rrampall on 10/02/18.
 */
@Entity
@Table(name="item_sale_history")
public class ItemSaleHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;


    @Getter
    @Setter
    @Column(name="item_id")
    private String itemId;

    @Getter
    @Setter
    private double price;

    @Getter
    @Setter
    private double quantity;

    @Getter
    @Setter
    private String units;

    @Getter
    @Setter
    @Column(name="time_stamp")
    private Date date;
}

package com.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by rrampall on 10/02/18.
 */
@Entity
@Table(name="item_purchase_history")
public class ItemPurchaseHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;


    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;


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

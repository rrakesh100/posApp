package com.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rrampall on 10/02/18.
 */
@Entity
@Table(name="item_updates")
public class ItemUpdate {

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
    private String field;

    @Getter
    @Setter
    private String value;

    @Getter
    @Setter
    @Column(name="time_stamp")
    private Date date;
}

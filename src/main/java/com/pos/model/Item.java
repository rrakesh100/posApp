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

    @Getter
    @Setter
    private boolean deleted;

    @Getter
    @Setter
    private double quantity;

    @Getter
    @Setter
    private String units;


    public boolean equals(Item i){
        if(i == null)
            return false;
        else if(!(i instanceof Item)) {
            return  false;
        }else if( i == this){
            return true;
        }else {
            return this.uid==i.uid;
        }
    }

    public int hashCode(){
        int result = 17;
        result = uid.hashCode() + 31 * result;
        result = (int) (price) + 31 * result;
        result = name.hashCode() + 31 * result;
        result = sku.hashCode() + 31 * result;
        return  result;
    }


}

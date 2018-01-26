package com.pos.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by rajithar on 21/1/18.
 */

@Entity
@Table(name="sales_items")
public class SaleItem {

  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;



  @Getter
  @Setter
  @OneToOne
  @JoinColumn(name = "item_id")
  private Item item;


  @Getter
  @Setter
  @Column(name="serial_number")
  private Long serialNumber;


  @Getter
  @Setter
  @Column(name="quantity_purchased")
  private Double quantity;


  //Price will vary each day for item. So don't keep reference to price in items table
  //Store here in a separate field as and when billing happens

  @Getter
  @Setter
  @Column(name="item_unit_price")
  private Double price;


  @Getter
  @Setter
  @Column(name="discount_percent")
  private Double discountPercent;


  @Getter
  @Setter
  @Column(name="tax_percent")
  private Double taxPercent;


  @Getter
  @Setter
  @Column(name="total_price")
  private Double totalPrice;


  @Getter
  @Setter
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name="sale_invoice_number" , nullable = false)
  private Sale sale;

}

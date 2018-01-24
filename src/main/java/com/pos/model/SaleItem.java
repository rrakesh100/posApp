package com.pos.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by rajithar on 21/1/18.
 */

@ToString
@Embeddable
@Table(name="sales_items")
public class SaleItem {
  @Getter
  @Setter
  @Column(name = "invoice_number")
  private Long invoiceNumber;
  @Getter
  @Setter
  @Column(name = "item_id")
  private int itemId;
  @Getter
  @Setter
  @Column(name="serial_number")
  private Long serialNumber;
  @Getter
  @Setter
  @Column(name="quantity_purchased")
  private Double quantity;
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
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = SaleItem.class)
  @JoinColumn(name="invoice_number" , nullable = false)
  private Sale sale;

}

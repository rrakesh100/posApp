package com.pos.model;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by rajithar on 13/1/18.
 */

@ToString
@Entity
@Table(name="procurement_items")
public class ProcurementItem {

  @Getter
  @Setter
  private Long procurementId;
  @Getter
  @Setter
  private Long itemId;
  @Getter
  @Setter
  private String description;
  @Getter
  @Setter
  @Column(name="serial_number")
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long serialNumber;
  @Getter
  @Setter
  @Column(name="quantity_purchased")
  private Double quantityPurchased;
  @Getter
  @Setter
  @Column(name="number_of_units")
  private int numberOfUnits;
  @Getter
  @Setter
  @Column(name="item_cost_price")
  private Double itemCostPrice;
  @Getter
  @Setter
  @Column(name="item_unit_price")
  private Double itemUnitPrice;
}

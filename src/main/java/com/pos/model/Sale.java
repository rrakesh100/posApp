package com.pos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by rajithar on 21/1/18.
 */

@ToString
@Entity
@Table(name="sales")
public class Sale {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Getter
  @Setter
  @Column(name = "invoice_number")
  private Long invoiceNumber;
  @Getter
  @Setter
  @Column(name = "sale_id")
  private Long saleId;
  @Getter
  @Setter
  @Column(name = "sale_time")
  private Date time;
  @Getter
  @Setter
  @Column(name = "sale_number")
  private String saleNumber;
  @Getter
  @Setter
  @Column(name = "employee_id")
  private int employeeId;
  @Getter
  @Setter
  @Column(name = "gross_amount")
  private Double grossAmount;
  @Getter
  @Setter
  @Column(name = "tax_percent")
  private Double taxPercent;
  @Getter
  @Setter
  private Double discount;
  @Getter
  @Setter
  @Column(name = "payment_amount")
  private Double paymentAmount;
  @Getter
  @Setter
  @Column(name = "tax_percent")
  private Double paymentType;
  @Getter
  @Setter
  private Double cgst;
  @Getter
  @Setter
  private Double sgst;
  @Getter
  @Setter
  private Double igst;
  @OneToMany
  @Getter
  @Setter
  private List<SaleItem> saleItems;

}

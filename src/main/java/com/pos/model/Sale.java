package com.pos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.pos.commons.EntityType.Employee;

/**
 * Created by rajithar on 21/1/18.
 */

@Entity
@Table(name="sales")
public class Sale {

  @Getter
  @Setter
  @Column(name = "invoice_number")
  @Id
  private String invoiceNumber;


  @Getter
  @Setter
  @Column(name = "sale_time")
  private Date saleTime;

  @Getter
  @Setter
  @Column(name = "sale_number")
  private String saleNumber;

  @Getter
  @Setter
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="customer_mobile_number")
  private Customer customer;


  @Getter
  @Setter
  @OneToOne
  @JoinColumn (name = "employee_id")
  private Employee employee;


  @Getter
  @Setter
  @Column(name = "sub_total")
  private Double subTotal;


  @Getter
  @Setter
  @Column(name = "tax_percent")
  private Double taxPercent;


  @Getter
  @Setter
  @Column(name = "tax_amount")
  private Double taxAmount;

  @Getter
  @Setter
  @Column(name = "net_amount")
  private Double netAmount;


  @Getter
  @Setter
  private Double discount;


  @Getter
  @Setter
  @Column(name = "total")
  private Double total;


  @Getter
  @Setter
  @Column(name = "payment_type")
  private String paymentType;


  @Getter
  @Setter
  @Column(name = "cgst")
  private Double cgst;


  @Getter
  @Setter
  @Column(name = "sgst")
  private Double sgst;


  @Getter
  @Setter
  @Column(name = "igst")
  private Double igst;


  @Getter
  @Setter
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "sale",  targetEntity = SaleItem.class,
          cascade = CascadeType.ALL)
  private List<SaleItem> saleItems;

}

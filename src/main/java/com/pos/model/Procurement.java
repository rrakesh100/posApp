package com.pos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by rajithar on 13/1/18.
 */

@Entity
@Table(name="procurements")
public class Procurement {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long procurementId;
  @Getter
  @Setter
  private Long supplierId;
  @Getter
  @Setter
  private Date time;
  @Getter
  @Setter
  private Long employeeId;
  @Getter
  @Setter
  private String comment;
  @Getter
  @Setter
  private String paymentType;
  @Getter
  @Setter
  private Double paymentAmount;

  @Getter
  @Setter
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "procurement",  targetEntity = ProcurementItem.class,
          cascade = CascadeType.ALL)
  private List<ProcurementItem> procurementItems;

}

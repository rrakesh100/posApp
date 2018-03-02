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
@Table(name="suppliers")
public class Supplier {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Getter
  @Setter
  private Long id;
  @Getter
  @Setter
  private String mobileNumber;
  @Getter
  @Setter
  private String companyName;
  @Getter
  @Setter
  private String agencyName;
  @Getter
  @Setter
  private boolean deleted;
//nocreated date?
}

package com.nttdada.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymenttypeDto {
  private String id;
  private String idTypePago;
  private String typePayment;
  private String updatedDate;
  private String creationDate;
  private int active;
}

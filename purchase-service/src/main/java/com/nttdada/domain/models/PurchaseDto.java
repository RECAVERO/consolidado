package com.nttdada.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
  private String id;
  private String idTypePago;
  private String amount;
  private String numberCuent;
  private String numberCell;
  private String numberTransaction;
  private String updatedDate;
  private String creationDate;
  private int active;
}

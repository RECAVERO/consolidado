package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDto {
  private String id;
  private String idClient;
  private String idType;
  private String idAccount;
  private String numberCuent;
  private String numberCard;
  private float balance;
  private int status;
  private int category;
  private int idMovementNumberCard;
  private String updatedDate;
  private String creationDate;
  private int active;
}

package com.nttdada.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurseDto {
  private String id;
  private String numberIdentity;
  private String numberCell;
  private String email;
  private String numberPurse;
  private float balance;
  private String updatedDate;
  private String creationDate;
  private int active;
}

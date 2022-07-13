package com.nttdada.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {
  private String id;
  private String idCurrency;
  private String typeCurrency;
  private float  priceSoles;
  private String updatedDate;
  private String createdDate;
  private int active;
}

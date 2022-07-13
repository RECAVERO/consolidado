package com.nttdada.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
  private String status;
  private String message;
  private CurrencyDto currency;
}

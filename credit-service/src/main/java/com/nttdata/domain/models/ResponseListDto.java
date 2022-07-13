package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListDto {
  private String status;
  private String msg;
  private List<CreditDto> listCreditDto;
}

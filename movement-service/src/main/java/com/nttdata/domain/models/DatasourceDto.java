package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasourceDto {
  private String status;
  private String msg;
  private ClientDto client;
  private TypeDto type;
  private AccountDto account;
  private List<MovementDto> movement;
  private List<CreditDto> credit;
}

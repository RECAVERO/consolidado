package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
  private String id;
  private String idClient;
  private String dni;
  private String names;
  private String email;
  private String updatedDate;
  private String createdDate;
  private int active;
}

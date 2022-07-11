package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
  private String id;
  private String idAccount;
  private String typeAccount;
  private String updatedDate;
  private String creationDate;
  private int active;
}

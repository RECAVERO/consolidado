package com.nttdata.infraestructure.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Accounts")
public class Account {
  @Id
  private String id;
  @NotEmpty
  private String idAccount;
  @NotEmpty
  private String typeAccount;
  @NotEmpty
  private String updatedDate;
  @NotEmpty
  private String creationDate;
  @NotEmpty
  private int active;
}

package com.nttdada.infraestructure.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Purses")
public class Purse {
  @Id
  private String id;
  @NotEmpty
  private String numberIdentity;
  @NotEmpty
  private String numberCell;
  @NotEmpty
  private String email;
  @NotEmpty
  private String numberPurse;
  @NotEmpty
  private float balance;
  @NotEmpty
  private String updatedDate;
  @NotEmpty
  private String creationDate;
  @NotEmpty
  private int active;
}

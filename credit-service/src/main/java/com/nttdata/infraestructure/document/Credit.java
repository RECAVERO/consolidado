package com.nttdata.infraestructure.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Credits")
public class Credit {
  @Id
  private String id;
  @NotEmpty
  private String idClient;
  @NotEmpty
  private String idType;
  @NotEmpty
  private String idAccount;
  @NotEmpty
  private String numberCuent;
  private String numberCard;
  private float balance;
  private int status;
  @NotEmpty
  private int category;
  @NotEmpty
  private String updatedDate;
  @NotEmpty
  private String creationDate;
  @NotEmpty
  private int active;
}

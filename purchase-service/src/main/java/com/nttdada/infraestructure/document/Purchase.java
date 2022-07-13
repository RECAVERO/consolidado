package com.nttdada.infraestructure.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("Purchases")
public class Purchase {
  @Id
  private String id;
  private String idTypePago;
  private String amount;
  private String numberCuent;
  private String numberCell;
  private String numberTransaction;
  private String updatedDate;
  private String creationDate;
  private int active;
}

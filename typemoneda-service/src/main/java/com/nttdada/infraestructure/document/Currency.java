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
@Document("coins")
public class Currency {
  @Id
  private String id;
  @NotEmpty
  private String idCurrency;
  @NotEmpty
  private String typeCurrency;
  @NotEmpty
  private float  priceSoles;
  @NotEmpty
  private String updatedDate;
  @NotEmpty
  private String createdDate;
  @NotEmpty
  private int active;
}

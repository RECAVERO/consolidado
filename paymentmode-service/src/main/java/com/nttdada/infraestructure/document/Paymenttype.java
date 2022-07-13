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
@Document("PaymentsTypes")
public class Paymenttype {
  @Id
  private String id;
  @NotEmpty
  private String idTypePago;
  @NotEmpty
  private String typePayment;
  @NotEmpty
  private String updatedDate;
  @NotEmpty
  private String creationDate;
  @NotEmpty
  private int active;
}

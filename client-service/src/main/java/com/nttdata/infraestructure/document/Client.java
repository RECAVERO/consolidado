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
@Document("Clients")
public class Client {
  @Id
  @NotEmpty
  private String id;
  @NotEmpty
  private String idClient;
  @NotEmpty
  private String dni;
  @NotEmpty
  private String names;
  @NotEmpty
  private String email;
  @NotEmpty
  private String updatedDate;
  @NotEmpty
  private String createdDate;
  @NotEmpty
  private int active;
}

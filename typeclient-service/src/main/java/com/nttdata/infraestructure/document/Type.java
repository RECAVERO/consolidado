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
@Document("Types")
public class Type {
  @Id
  private String id;
  @NotEmpty
  private String idType;
  @NotEmpty
  private String typeClient;
  @NotEmpty
  private String creationDate;
  @NotEmpty
  private int active;
}

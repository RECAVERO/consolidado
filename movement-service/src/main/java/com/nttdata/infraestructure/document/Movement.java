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
@Document("Movements")
public class Movement {
    @Id
    private String id;
    @NotEmpty
    private String operation;
    @NotEmpty
    private String idClient;
    @NotEmpty
    private String idType;
    @NotEmpty
    private String idAccount;
    @NotEmpty
    private String numberCuent;
    @NotEmpty
    private float amount;
    @NotEmpty
    private int status;
    @NotEmpty
    private String numberCard;
    @NotEmpty
    private int category;
    private int idMovementNumberCard;
    @NotEmpty
    private String updatedDate;
    @NotEmpty
    private String creationDate;
    @NotEmpty
    private int active;
}

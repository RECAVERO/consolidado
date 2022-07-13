package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {
    private String id;
    private String operation;
    private String idClient;
    private String idType;
    private String idAccount;
    private String numberCuent;
    private float amount;
    private int status;
    private String numberCard;
    private int category;
    private int idMovementNumberCard;
    private String updatedDate;
    private String creationDate;
    private int active;
}

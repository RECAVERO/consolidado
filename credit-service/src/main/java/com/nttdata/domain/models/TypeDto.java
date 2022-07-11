package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDto {
    private String id;
    private String idType;
    private String typeClient;
    private String updatedDate;
    private String creationDate;
    private int active;
}

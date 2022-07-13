package com.nttdada.utils.convert;

import com.nttdada.domain.models.PurseDto;
import com.nttdada.infraestructure.document.Purse;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static PurseDto entityToDto(Purse purse) {
    PurseDto purseDto = new PurseDto();
    BeanUtils.copyProperties(purse, purseDto);
    return purseDto;
  }

  public static Purse dtoToEntity(PurseDto purseDto) {
    Purse purse = new Purse();
    BeanUtils.copyProperties(purseDto, purse);
    return purse;
  }
}

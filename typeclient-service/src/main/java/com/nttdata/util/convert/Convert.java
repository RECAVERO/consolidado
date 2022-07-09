package com.nttdata.util.convert;

import com.nttdata.domain.models.TypeDto;
import com.nttdata.infraestructure.document.Type;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static TypeDto entityToDto(Type type) {
    TypeDto typeDto = new TypeDto();
    BeanUtils.copyProperties(type, typeDto);
    return typeDto;
  }

  public static Type dtoToEntity(TypeDto typeDto) {
    Type type = new Type();
    BeanUtils.copyProperties(typeDto, type);
    return type;
  }
}
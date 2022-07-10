package com.nttdata.utils.convert;

import com.nttdata.domain.models.CreditDto;
import com.nttdata.infraestructure.document.Credit;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static CreditDto entityToDto(Credit credit) {
    CreditDto creditDto = new CreditDto();
    BeanUtils.copyProperties(credit, creditDto);
    return creditDto;
  }

  public static Credit dtoToEntity(CreditDto creditDto) {
    Credit credit = new Credit();
    BeanUtils.copyProperties(creditDto, credit);
    return credit;
  }
}

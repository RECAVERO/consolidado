package com.nttdada.utils.convert;

import com.nttdada.domain.models.PaymenttypeDto;
import com.nttdada.infraestructure.document.Paymenttype;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static PaymenttypeDto entityToDto(Paymenttype paymenttype) {
    PaymenttypeDto paymenttypeDto = new PaymenttypeDto();
    BeanUtils.copyProperties(paymenttype, paymenttypeDto);
    return paymenttypeDto;
  }

  public static Paymenttype dtoToEntity(PaymenttypeDto purseDto) {
    Paymenttype paymenttype = new Paymenttype();
    BeanUtils.copyProperties(purseDto, paymenttype);
    return paymenttype;
  }
}

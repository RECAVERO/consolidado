package com.nttdada.utils.convert;

import com.nttdada.domain.models.CurrencyDto;
import com.nttdada.infraestructure.document.Currency;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static CurrencyDto entityToDto(Currency currency) {
    CurrencyDto currencyDto = new CurrencyDto();
    BeanUtils.copyProperties(currency, currencyDto);
    return currencyDto;
  }

  public static Currency dtoToEntity(CurrencyDto currencyDto) {
    Currency currency = new Currency();
    BeanUtils.copyProperties(currencyDto, currency);
    return currency;
  }
}

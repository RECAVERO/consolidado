package com.nttdada.utils.convert;

import com.nttdada.domain.models.PurchaseDto;
import com.nttdada.infraestructure.document.Purchase;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static PurchaseDto entityToDto(Purchase purchase) {
    PurchaseDto purchaseDto = new PurchaseDto();
    BeanUtils.copyProperties(purchase, purchaseDto);
    return purchaseDto;
  }

  public static Purchase dtoToEntity(PurchaseDto currencyDto) {
    Purchase purchase = new Purchase();
    BeanUtils.copyProperties(currencyDto, purchase);
    return purchase;
  }
}

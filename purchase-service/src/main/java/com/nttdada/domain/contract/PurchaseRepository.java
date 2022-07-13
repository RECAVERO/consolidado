package com.nttdada.domain.contract;

import com.nttdada.domain.models.PurchaseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurchaseRepository {
  Flux<PurchaseDto> getListPurchase();
  Mono<PurchaseDto> savePurchase(Mono<PurchaseDto> purchaseDto);
  Mono<PurchaseDto> updatePurchase(Mono<PurchaseDto> purchaseDto, String id);
  Mono<PurchaseDto> getByIdPurchase(String id);
  Mono<Void> deleteById(String id);

  //Mono<PurchaseDto> findByIdCurrency(String idCurrency);
}

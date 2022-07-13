package com.nttdada.btask.interfaces;

import com.nttdada.domain.models.PurchaseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurchaseService {
  Flux<PurchaseDto> getListPurchase();
  Mono<PurchaseDto> savePurchase(Mono<PurchaseDto> purchaseDto);
  Mono<PurchaseDto> updatePurchase(Mono<PurchaseDto> purchaseDto, String id);
  Mono<PurchaseDto> getByIdPurchase(String id);
  Mono<Void> deleteById(String id);
}

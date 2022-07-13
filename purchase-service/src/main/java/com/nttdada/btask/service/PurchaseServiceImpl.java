package com.nttdada.btask.service;

import com.nttdada.btask.interfaces.PurchaseService;
import com.nttdada.domain.contract.PurchaseRepository;
import com.nttdada.domain.models.PurchaseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurchaseServiceImpl implements PurchaseService {
  private final PurchaseRepository purchaseRepository;

  public PurchaseServiceImpl(PurchaseRepository purchaseRepository) {
    this.purchaseRepository = purchaseRepository;
  }

  @Override
  public Flux<PurchaseDto> getListPurchase() {
    return this.purchaseRepository.getListPurchase();
  }

  @Override
  public Mono<PurchaseDto> savePurchase(Mono<PurchaseDto> purchaseDto) {
    return this.purchaseRepository.savePurchase(purchaseDto);
  }

  @Override
  public Mono<PurchaseDto> updatePurchase(Mono<PurchaseDto> purchaseDto, String id) {
    return this.purchaseRepository.updatePurchase(purchaseDto, id);
  }

  @Override
  public Mono<PurchaseDto> getByIdPurchase(String id) {
    return this.purchaseRepository.getByIdPurchase(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.purchaseRepository.deleteById(id);
  }
}

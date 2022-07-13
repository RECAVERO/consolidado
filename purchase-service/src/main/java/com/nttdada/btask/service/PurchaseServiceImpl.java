package com.nttdada.btask.service;

import com.nttdada.btask.interfaces.PurchaseService;
import com.nttdada.domain.contract.PurchaseRepository;
import com.nttdada.domain.models.PurchaseDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurchaseServiceImpl implements PurchaseService {
  private final PurchaseRepository purchaseRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public PurchaseServiceImpl(PurchaseRepository purchaseRepository, KafkaTemplate<String, String> kafkaTemplate) {
    this.purchaseRepository = purchaseRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public Flux<PurchaseDto> getListPurchase() {
    return this.purchaseRepository.getListPurchase();
  }

  @Override
  public Mono<PurchaseDto> savePurchase(Mono<PurchaseDto> purchaseDto) {
    return this.purchaseRepository.savePurchase(purchaseDto).flatMap(purchase->{
      System.out.println("Received " + purchase);
      this.kafkaTemplate.send("topic-2",purchase.toString());
      return Mono.just(purchase);
    });
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

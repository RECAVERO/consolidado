package com.nttdada.infraestructure.repository;

import com.nttdada.domain.contract.PurchaseRepository;
import com.nttdada.domain.models.PurchaseDto;
import com.nttdada.infraestructure.mongodb.PurchaseRepositoryMongodb;
import com.nttdada.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public class PurchaseRepositoryImpl implements PurchaseRepository {
  private final PurchaseRepositoryMongodb purchaseRepositoryMongodb;

  public PurchaseRepositoryImpl(PurchaseRepositoryMongodb purchaseRepositoryMongodb) {
    this.purchaseRepositoryMongodb = purchaseRepositoryMongodb;
  }

  @Override
  public Flux<PurchaseDto> getListPurchase() {
    return this.purchaseRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<PurchaseDto> savePurchase(Mono<PurchaseDto> purchaseDto) {
    return purchaseDto.map(Convert::dtoToEntity)
        .flatMap(this.purchaseRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<PurchaseDto> updatePurchase(Mono<PurchaseDto> purchaseDto, String id) {
    return  this.purchaseRepositoryMongodb.findById(id)
        .flatMap(p -> purchaseDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.purchaseRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<PurchaseDto> getByIdPurchase(String id) {
    return this.purchaseRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new PurchaseDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.purchaseRepositoryMongodb.deleteById(id);
  }
}

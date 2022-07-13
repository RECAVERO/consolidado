package com.nttdada.infraestructure.respository;

import com.nttdada.domain.contract.PaymentTypeRepository;
import com.nttdada.domain.models.PaymenttypeDto;
import com.nttdada.infraestructure.mongodb.PaymentTypeRepositoryMongodb;
import com.nttdada.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PaymentTypeRepositoryImpl implements PaymentTypeRepository {
  private final PaymentTypeRepositoryMongodb paymentTypeRepositoryMongodb;

  public PaymentTypeRepositoryImpl(PaymentTypeRepositoryMongodb paymentTypeRepositoryMongodb) {
    this.paymentTypeRepositoryMongodb = paymentTypeRepositoryMongodb;
  }

  @Override
  public Flux<PaymenttypeDto> getListPaymentType() {
    return this.paymentTypeRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<PaymenttypeDto> savePaymentType(Mono<PaymenttypeDto> paymentTypeDto) {
    return paymentTypeDto.map(Convert::dtoToEntity)
        .flatMap(this.paymentTypeRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<PaymenttypeDto> updatePaymentType(Mono<PaymenttypeDto> paymentTypeDto, String id) {
    return  this.paymentTypeRepositoryMongodb.findById(id)
        .flatMap(p -> paymentTypeDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.paymentTypeRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<PaymenttypeDto> getByIdPaymentType(String id) {
    return this.paymentTypeRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new PaymenttypeDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.paymentTypeRepositoryMongodb.deleteById(id);
  }

  @Override
  public Mono<PaymenttypeDto> findByIdPaymentType(String idPaymentType) {
    return this.paymentTypeRepositoryMongodb.findById(idPaymentType)
        .map(Convert::entityToDto).defaultIfEmpty(new PaymenttypeDto());
  }
}

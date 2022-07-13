package com.nttdada.domain.contract;

import com.nttdada.domain.models.PaymenttypeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentTypeRepository {
  Flux<PaymenttypeDto> getListPaymentType();
  Mono<PaymenttypeDto> savePaymentType(Mono<PaymenttypeDto> paymentTypeDto);
  Mono<PaymenttypeDto> updatePaymentType(Mono<PaymenttypeDto> paymentTypeDto, String id);
  Mono<PaymenttypeDto> getByIdPaymentType(String id);
  Mono<Void> deleteById(String id);

  Mono<PaymenttypeDto> findByIdPaymentType(String idPaymentType);
}

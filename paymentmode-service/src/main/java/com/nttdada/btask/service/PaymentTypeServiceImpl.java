package com.nttdada.btask.service;

import com.nttdada.btask.interfaces.PaymentTypeService;
import com.nttdada.domain.contract.PaymentTypeRepository;
import com.nttdada.domain.models.PaymenttypeDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {
  private final PaymentTypeRepository paymentTypeRepository;

  public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository) {
    this.paymentTypeRepository = paymentTypeRepository;
  }

  @Override
  public Flux<PaymenttypeDto> getListPaymentType() {
    return this.paymentTypeRepository.getListPaymentType();
  }

  @Override
  public Mono<PaymenttypeDto> savePaymentType(Mono<PaymenttypeDto> paymentTypeDto) {
    return this.paymentTypeRepository.savePaymentType(paymentTypeDto);
  }

  @Override
  public Mono<PaymenttypeDto> updatePaymentType(Mono<PaymenttypeDto> paymentTypeDto, String id) {
    return this.paymentTypeRepository.updatePaymentType(paymentTypeDto, id);
  }

  @Override
  public Mono<PaymenttypeDto> getByIdPaymentType(String id) {
    return this.paymentTypeRepository.getByIdPaymentType(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.paymentTypeRepository.deleteById(id);
  }

  @Override
  public Mono<PaymenttypeDto> findByIdPaymentType(String idPaymentType) {
    return this.paymentTypeRepository.getByIdPaymentType(idPaymentType);
  }
}

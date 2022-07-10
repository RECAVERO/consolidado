package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.CreditService;
import com.nttdata.domain.contract.CreditRepository;
import com.nttdata.domain.models.CreditDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class CreditServiceImpl implements CreditService {
  private final CreditRepository creditRepository;

  public CreditServiceImpl(CreditRepository creditRepository) {
    this.creditRepository = creditRepository;
  }

  @Override
  public Flux<CreditDto> getListCredit() {
    return this.creditRepository.getListCredit();
  }

  @Override
  public Mono<CreditDto> saveCredit(Mono<CreditDto> creditDto) {
    return this.creditRepository.saveCredit(creditDto);
  }

  @Override
  public Mono<CreditDto> updateCredit(Mono<CreditDto> creditDto, String id) {
    return this.creditRepository.updateCredit(creditDto, id);
  }

  @Override
  public Mono<CreditDto> getByIdCredit(String id) {
    return this.creditRepository.getByIdCredit(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.creditRepository.deleteById(id);
  }
}

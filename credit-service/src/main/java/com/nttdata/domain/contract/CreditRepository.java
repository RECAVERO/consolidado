package com.nttdata.domain.contract;

import com.nttdata.domain.models.CreditDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepository {
  Flux<CreditDto> getListCredit();
  Mono<CreditDto> saveCredit(Mono<CreditDto> creditDto);
  Mono<CreditDto> updateCredit(Mono<CreditDto> creditDto, String id);
  Mono<CreditDto> getByIdCredit(String id);
  Mono<Void> deleteById(String id);
}

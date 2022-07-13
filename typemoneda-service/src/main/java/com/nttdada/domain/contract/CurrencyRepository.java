package com.nttdada.domain.contract;

import com.nttdada.domain.models.CurrencyDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyRepository {
  Flux<CurrencyDto> getListCurrency();
  Mono<CurrencyDto> saveCurrency(Mono<CurrencyDto> currencyDto);
  Mono<CurrencyDto> updateCurrency(Mono<CurrencyDto> currencyDto, String id);
  Mono<CurrencyDto> getByIdCurrency(String id);
  Mono<Void> deleteById(String id);

  Mono<CurrencyDto> findByIdCurrency(String idCurrency);
}

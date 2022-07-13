package com.nttdada.btask.service;

import com.nttdada.btask.interfaces.CurrencyService;
import com.nttdada.domain.contract.CurrencyRepository;
import com.nttdada.domain.models.CurrencyDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrencyServiceImpl implements CurrencyService {
  private final CurrencyRepository currencyRepository;

  public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
    this.currencyRepository = currencyRepository;
  }

  @Override
  public Flux<CurrencyDto> getListCurrency() {
    return this.currencyRepository.getListCurrency();
  }

  @Override
  public Mono<CurrencyDto> saveCurrency(Mono<CurrencyDto> currencyDto) {
    return this.currencyRepository.saveCurrency(currencyDto);
  }

  @Override
  public Mono<CurrencyDto> updateCurrency(Mono<CurrencyDto> currencyDto, String id) {
    return this.currencyRepository.updateCurrency(currencyDto, id);
  }

  @Override
  public Mono<CurrencyDto> getByIdCurrency(String id) {
    return this.currencyRepository.getByIdCurrency(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.currencyRepository.deleteById(id);
  }

  @Override
  public Mono<CurrencyDto> findByIdCurrency(String idCurrency) {
    return this.currencyRepository.findByIdCurrency(idCurrency);
  }
}

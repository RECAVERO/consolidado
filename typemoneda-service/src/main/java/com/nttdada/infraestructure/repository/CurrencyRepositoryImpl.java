package com.nttdada.infraestructure.repository;

import com.nttdada.domain.contract.CurrencyRepository;
import com.nttdada.domain.models.CurrencyDto;
import com.nttdada.infraestructure.mongodb.CurrencyRepositoryMongodb;
import com.nttdada.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {
  private final CurrencyRepositoryMongodb currencyRepositoryMongodb;

  public CurrencyRepositoryImpl(CurrencyRepositoryMongodb currencyRepositoryMongodb) {
    this.currencyRepositoryMongodb = currencyRepositoryMongodb;
  }

  @Override
  public Flux<CurrencyDto> getListCurrency() {
    return this.currencyRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<CurrencyDto> saveCurrency(Mono<CurrencyDto> currencyDto) {
    return currencyDto.map(Convert::dtoToEntity)
        .flatMap(this.currencyRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<CurrencyDto> updateCurrency(Mono<CurrencyDto> currencyDto, String id) {
    return  this.currencyRepositoryMongodb.findById(id)
        .flatMap(p -> currencyDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.currencyRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<CurrencyDto> getByIdCurrency(String id) {
    return this.currencyRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new CurrencyDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.currencyRepositoryMongodb.deleteById(id);
  }

  @Override
  public Mono<CurrencyDto> findByIdCurrency(String idCurrency) {
    return this.currencyRepositoryMongodb.findByIdCurrency(idCurrency).defaultIfEmpty(new CurrencyDto());
  }
}

package com.nttdada.infraestructure.mongodb;

import com.nttdada.domain.models.CurrencyDto;
import com.nttdada.infraestructure.document.Currency;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyRepositoryMongodb extends ReactiveMongoRepository<Currency, String> {
  Mono<CurrencyDto> findByIdCurrency(String idCurrency);
}

package com.nttdata.infraestructure.mongodb;

import com.nttdata.domain.models.CreditDto;
import com.nttdata.infraestructure.document.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepositoryMongodb extends ReactiveMongoRepository<Credit, String> {
    Mono<CreditDto> findByIdClientAndIdTypeAndIdAccount(String idClient, String idType, String idAccount);
    Mono<CreditDto> findByIdClientAndIdTypeAndIdAccountAndNumberCuent(String idClient, String idType, String idAccount, String numberCuent);

    Mono<CreditDto> findByNumberCuent(String numberCuent);

    Flux<CreditDto> findByIdClient(String idClient);

    Flux<CreditDto> findByIdClientAndIdTypeAndIdAccountAndCreationDateBetween(String idClient, String idType, String idAccount, String dateStart, String dateEnd);

    Flux<CreditDto> findByNumberCard(String numberCard);

    Mono<CreditDto> findByNumberCardAndCategory(String numberCard, int category);



}

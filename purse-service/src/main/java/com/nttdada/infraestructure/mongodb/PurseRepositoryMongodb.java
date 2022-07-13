package com.nttdada.infraestructure.mongodb;

import com.nttdada.domain.models.PurseDto;
import com.nttdada.infraestructure.document.Purse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurseRepositoryMongodb extends ReactiveMongoRepository<Purse, String> {

  //Mono<PurseDto> findByIdPurse(String idPurse);

  Mono<PurseDto> findByNumberIdentity(String numberIdentity);
  Mono<PurseDto> findByNumberCell(String numberCell);
  Mono<PurseDto> findByEmail(String email);
}

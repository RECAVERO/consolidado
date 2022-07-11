package com.nttdata.infraestructure.mongodb;

import com.nttdata.domain.models.TypeDto;
import com.nttdata.infraestructure.document.Type;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TypeRepositoryMongodb extends ReactiveMongoRepository<Type, String> {
    Mono<TypeDto> findByIdType(String idType);
}

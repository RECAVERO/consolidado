package com.nttdata.infraestructure.mongodb;

import com.nttdata.domain.models.ClientDto;
import com.nttdata.infraestructure.document.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientRepositoryMongodb extends ReactiveMongoRepository<Client, String> {
    Mono<ClientDto> findByIdClient(String idClient);
}

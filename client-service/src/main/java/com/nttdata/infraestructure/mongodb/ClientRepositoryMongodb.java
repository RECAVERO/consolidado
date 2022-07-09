package com.nttdata.infraestructure.mongodb;

import com.nttdata.infraestructure.document.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientRepositoryMongodb extends ReactiveMongoRepository<Client, String> {
}

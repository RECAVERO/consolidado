package com.nttdata.infraestructure.mongodb;

import com.nttdata.infraestructure.document.Type;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TypeRepositoryMongodb extends ReactiveMongoRepository<Type, String> {
}

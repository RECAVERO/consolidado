package com.nttdata.infraestructure.mongodb;

import com.nttdata.infraestructure.document.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditRepositoryMongodb extends ReactiveMongoRepository<Credit, String> {
}

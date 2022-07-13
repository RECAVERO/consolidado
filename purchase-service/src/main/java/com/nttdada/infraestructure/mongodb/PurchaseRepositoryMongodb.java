package com.nttdada.infraestructure.mongodb;

import com.nttdada.infraestructure.document.Purchase;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PurchaseRepositoryMongodb extends ReactiveMongoRepository<Purchase, String> {
}

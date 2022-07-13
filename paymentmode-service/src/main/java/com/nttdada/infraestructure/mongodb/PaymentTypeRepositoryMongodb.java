package com.nttdada.infraestructure.mongodb;

import com.nttdada.infraestructure.document.Paymenttype;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PaymentTypeRepositoryMongodb extends ReactiveMongoRepository<Paymenttype, String> {
}

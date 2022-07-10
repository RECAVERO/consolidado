package com.nttdata.infraestructure.mongodb;

import com.nttdata.infraestructure.document.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepositoryMongodb extends ReactiveMongoRepository<Account, String> {
}

package com.nttdata.infraestructure.mongodb;

import com.nttdata.domain.models.AccountDto;
import com.nttdata.infraestructure.document.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepositoryMongodb extends ReactiveMongoRepository<Account, String> {
    Mono<AccountDto> findByIdAccount(String idAccount);
}

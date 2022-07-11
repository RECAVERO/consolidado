package com.nttdata.domain.contract;

import com.nttdata.domain.models.AccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository {
  Flux<AccountDto> getListAccount();
  Mono<AccountDto> saveAccount(Mono<AccountDto> accountDto);
  Mono<AccountDto> updateAccount(Mono<AccountDto> accountDto, String id);
  Mono<AccountDto> getByIdAccount(String id);
  Mono<Void> deleteById(String id);
  Mono<AccountDto> getByIdProduct(String idAccount);
}

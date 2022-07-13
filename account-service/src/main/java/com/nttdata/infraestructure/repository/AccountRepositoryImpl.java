package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.AccountRepository;
import com.nttdata.domain.models.AccountDto;
import com.nttdata.infraestructure.mongodb.AccountRepositoryMongodb;
import com.nttdata.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
  private final AccountRepositoryMongodb accountRepositoryMongodb;

  public AccountRepositoryImpl(AccountRepositoryMongodb accountRepositoryMongodb) {
    this.accountRepositoryMongodb = accountRepositoryMongodb;
  }


  @Override
  public Flux<AccountDto> getListAccount() {
    return this.accountRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<AccountDto> saveAccount(Mono<AccountDto> accountDto) {
    return accountDto.map(Convert::dtoToEntity)
        .flatMap(this.accountRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<AccountDto> updateAccount(Mono<AccountDto> accountDto, String id) {
    return  this.accountRepositoryMongodb.findById(id)
        .flatMap(p -> accountDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.accountRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<AccountDto> getByIdAccount(String id) {
    return this.accountRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new AccountDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.accountRepositoryMongodb.deleteById(id);
  }

  @Override
  public Mono<AccountDto> getByIdProduct(String idAccount) {
    return this.accountRepositoryMongodb.findByIdAccount(idAccount).defaultIfEmpty(new AccountDto());
  }
}

package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.AccountService;
import com.nttdata.domain.contract.AccountRepository;
import com.nttdata.domain.models.AccountDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public Flux<AccountDto> getListAccount() {
    return this.accountRepository.getListAccount();
  }

  @Override
  public Mono<AccountDto> saveAccount(Mono<AccountDto> accountDto) {
    return this.accountRepository.saveAccount(accountDto);
  }

  @Override
  public Mono<AccountDto> updateAccount(Mono<AccountDto> accountDto, String id) {
    return this.accountRepository.updateAccount(accountDto, id);
  }

  @Override
  public Mono<AccountDto> getByIdAccount(String id) {
    return this.accountRepository.getByIdAccount(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.accountRepository.deleteById(id);
  }

  @Override
  public Mono<AccountDto> getByIdProduct(String idAccount) {
    return this.accountRepository.getByIdProduct(idAccount);
  }
}

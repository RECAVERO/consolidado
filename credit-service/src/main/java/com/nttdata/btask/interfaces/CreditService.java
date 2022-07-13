package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.AccountDto;
import com.nttdata.domain.models.ClientDto;
import com.nttdata.domain.models.CreditDto;
import com.nttdata.domain.models.TypeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
  Flux<CreditDto> getListCredit();
  Mono<CreditDto> saveCredit(Mono<CreditDto> creditDto);
  Mono<CreditDto> updateCredit(Mono<CreditDto> creditDto, String id);
  Mono<CreditDto> getByIdCredit(String id);
  Mono<Void> deleteById(String id);

  //req 4

  Mono<ClientDto> getByIdClient(String idClient);

  Mono<TypeDto> getByIdType(String idType);

  Mono<AccountDto> getByIdAccount(String idAccount);

  Mono<CreditDto> getByIdClientAndIdTypeAndIdAccount(String idClient, String idType, String idAccount);

  Mono<CreditDto> getByIdClientAndIdTypeAndIdAccountAndNumberCuent(String idClient, String idType, String idAccount, String numberCuent);

  Mono<CreditDto> getCreditByNumberCuent(String numberCuent);
  Mono<ClientDto> saveClient(Mono<ClientDto> clientDto);

  Flux<CreditDto> getCreditByIdClient(String idClient);

  Flux<CreditDto> getCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idAccount, String dateStart, String dateEnd);
  Flux<CreditDto> getCreditByNumberCard(String NumberCard);

  Mono<CreditDto> getCreditByNumberCardAndCategory(String numberCard, int category);

}

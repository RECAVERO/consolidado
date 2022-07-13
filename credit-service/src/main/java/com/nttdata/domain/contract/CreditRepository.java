package com.nttdata.domain.contract;

import com.nttdata.domain.models.CreditDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditRepository {
  Flux<CreditDto> getListCredit();
  Mono<CreditDto> saveCredit(Mono<CreditDto> creditDto);
  Mono<CreditDto> updateCredit(Mono<CreditDto> creditDto, String id);
  Mono<CreditDto> getByIdCredit(String id);
  Mono<Void> deleteById(String id);

  //req 4

  Mono<CreditDto> getByIdClientAndIdTypeAndIdAccount(String idClient, String idType, String idAccount);
  Mono<CreditDto> getByIdClientAndIdTypeAndIdAccountAndNumberCuent(String idClient, String idType, String idAccount, String numberCuent);

  Mono<CreditDto> getCreditByNumberCuent(String numberCuent);

  /*------pro03------*/
  Flux<CreditDto> getCreditByIdClient(String idClient);

  Flux<CreditDto> getCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idAccount, String dateStart, String dateEnd);

  Flux<CreditDto> getCreditByNumberCard(String NumberCard);
  Mono<CreditDto> getCreditByNumberCardAndCategory(String numberCard, int category);




}

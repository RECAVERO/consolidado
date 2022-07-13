package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Flux<MovementDto> getListMovement();
    Mono<MovementDto> saveMovement(Mono<MovementDto> movementDto);
    Mono<MovementDto> updateMovement(Mono<MovementDto> movementDto, String id);
    Mono<MovementDto> getByIdMovement(String id);
    Mono<Void> deleteById(String id);

    Mono<CreditDto> getCredit(String idClient, String idType, String idAccount, String numberCuent);
    Mono<CreditDto> updateCreditDeposit(Mono<CreditDto> creditDto);
    Mono<CreditDto> updateCreditWithdrawal(Mono<CreditDto> creditDto);

    Flux<MovementDto> getListMovementByIdClientAndNumberCuent(String idClient, String numberCuent);

    Mono<ClientDto> getByIdClient(String idClient);
    Mono<TypeDto> getByIdType(String idType);
    Mono<AccountDto> getByIdAccount(String idAccount);

    Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient,
                                                                                           String idType,
                                                                                           String idAccount,
                                                                                           String dateStart,
                                                                                           String dateEnd);
    Flux<CreditDto> getListCreditByIdClientAndIdTypeAndIdAccountAndCreationDateBetween(String idClient,
                                                                                       String idType,
                                                                                       String idAccount,
                                                                                       String dateStart,
                                                                                       String dateEnd);

    Mono<CreditDto> getCreditByIdNumberCard(String numberCard);

    Flux<MovementDto> getListMovementByIdNumberCard(String numberCard);
}

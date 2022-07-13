package com.nttdata.domain.contract;

import com.nttdata.domain.models.MovementDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepository {
    Flux<MovementDto> getListMovement();
    Mono<MovementDto> saveMovement(Mono<MovementDto> movementDto);
    Mono<MovementDto> updateMovement(Mono<MovementDto> movementDto, String id);
    Mono<MovementDto> getByIdMovement(String id);
    Mono<Void> deleteById(String id);

    Flux<MovementDto> getListMovementByIdClientAndNumberCuent(String idClient, String numberCuent);

    Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient,
                                                                                           String idType,
                                                                                           String idProduct,
                                                                                           String dateStart,
                                                                                           String dateEnd);

    Flux<MovementDto> getListMovementByIdNumberCard(String numberCard);
}

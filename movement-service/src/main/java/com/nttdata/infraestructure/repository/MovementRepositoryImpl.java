package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.MovementRepository;
import com.nttdata.domain.models.MovementDto;
import com.nttdata.infraestructure.mongodb.MovementRepositoryMongodb;
import com.nttdata.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public class MovementRepositoryImpl implements MovementRepository {
    private final MovementRepositoryMongodb movementRepositoryMongodb;

    public MovementRepositoryImpl(MovementRepositoryMongodb movementRepositoryMongodb) {
        this.movementRepositoryMongodb = movementRepositoryMongodb;
    }

    @Override
    public Flux<MovementDto> getListMovement() {
        return this.movementRepositoryMongodb.findAll().map(Convert::entityToDto);
    }

    @Override
    public Mono<MovementDto> saveMovement(Mono<MovementDto> movementDto) {
        return movementDto.map(Convert::dtoToEntity)
                .flatMap(this.movementRepositoryMongodb::insert)
                .map(Convert::entityToDto);
    }

    @Override
    public Mono<MovementDto> updateMovement(Mono<MovementDto> movementDto, String id) {
        return  this.movementRepositoryMongodb.findById(id)
                .flatMap(p -> movementDto.map(Convert::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.movementRepositoryMongodb::save)
                .map(Convert::entityToDto);
    }

    @Override
    public Mono<MovementDto> getByIdMovement(String id) {
        return this.movementRepositoryMongodb.findById(id)
                .map(Convert::entityToDto).defaultIfEmpty(new MovementDto());
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return this.movementRepositoryMongodb.deleteById(id);
    }

    @Override
    public Flux<MovementDto> getListMovementByIdClientAndNumberCuent(String idClient, String numberCuent) {
        return this.movementRepositoryMongodb.findByIdClientAndNumberCuent(idClient, numberCuent);
    }

    @Override
    public Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd) {
        return this.movementRepositoryMongodb.findByIdClientAndIdTypeAndIdAccountAndCreationDateBetween(idClient, idType, idProduct, dateStart, dateEnd).defaultIfEmpty(new MovementDto());
    }

    @Override
    public Flux<MovementDto> getListMovementByIdNumberCard(String numberCard) {
        return this.movementRepositoryMongodb.findByNumberCard(numberCard);
    }
}

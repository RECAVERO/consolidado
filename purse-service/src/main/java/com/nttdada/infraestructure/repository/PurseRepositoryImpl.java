package com.nttdada.infraestructure.repository;

import com.nttdada.domain.contract.PurseRepository;
import com.nttdada.domain.models.PurseDto;
import com.nttdada.infraestructure.mongodb.PurseRepositoryMongodb;
import com.nttdada.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PurseRepositoryImpl implements PurseRepository {
  private final PurseRepositoryMongodb purseRepositoryMongodb;

  public PurseRepositoryImpl(PurseRepositoryMongodb purseRepositoryMongodb) {
    this.purseRepositoryMongodb = purseRepositoryMongodb;
  }

  @Override
  public Flux<PurseDto> getListPurse() {
    return this.purseRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<PurseDto> savePurse(Mono<PurseDto> purseDto) {
    return purseDto.map(Convert::dtoToEntity)
        .flatMap(this.purseRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<PurseDto> updatePurse(Mono<PurseDto> purseDto, String id) {
    return  this.purseRepositoryMongodb.findById(id)
        .flatMap(p -> purseDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.purseRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<PurseDto> getByIdPurse(String id) {
    return this.purseRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new PurseDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.purseRepositoryMongodb.deleteById(id);
  }

  @Override
  public Mono<PurseDto> findByNumberIdentity(String numberIdentity) {
    return this.purseRepositoryMongodb.findByNumberIdentity(numberIdentity).defaultIfEmpty(new PurseDto());
  }

  @Override
  public Mono<PurseDto> findByNumberCell(String numberCell) {
    return this.purseRepositoryMongodb.findByNumberCell(numberCell).defaultIfEmpty(new PurseDto());
  }

  @Override
  public Mono<PurseDto> findByEmail(String email) {
    return this.purseRepositoryMongodb.findByEmail(email).defaultIfEmpty(new PurseDto());
  }
}

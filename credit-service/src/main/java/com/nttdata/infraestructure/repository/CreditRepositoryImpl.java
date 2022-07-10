package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.CreditRepository;
import com.nttdata.domain.models.CreditDto;
import com.nttdata.infraestructure.mongodb.CreditRepositoryMongodb;
import com.nttdata.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public class CreditRepositoryImpl implements CreditRepository {
  private final CreditRepositoryMongodb creditRepositoryMongodb;

  public CreditRepositoryImpl(CreditRepositoryMongodb creditRepositoryMongodb) {
    this.creditRepositoryMongodb = creditRepositoryMongodb;
  }

  @Override
  public Flux<CreditDto> getListCredit() {
    return this.creditRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<CreditDto> saveCredit(Mono<CreditDto> creditDto) {
    return creditDto.map(Convert::dtoToEntity)
        .flatMap(this.creditRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<CreditDto> updateCredit(Mono<CreditDto> creditDto, String id) {
    return  this.creditRepositoryMongodb.findById(id)
        .flatMap(p -> creditDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.creditRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<CreditDto> getByIdCredit(String id) {
    return this.creditRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.creditRepositoryMongodb.deleteById(id);
  }
}

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

  @Override
  public Mono<CreditDto> getByIdClientAndIdTypeAndIdAccount(String idClient, String idType, String idAccount) {
    return this.creditRepositoryMongodb.findByIdClientAndIdTypeAndIdAccount(idClient, idType, idAccount).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Mono<CreditDto> getByIdClientAndIdTypeAndIdAccountAndNumberCuent(String idClient, String idType, String idAccount, String numberCuent) {
    return this.creditRepositoryMongodb.findByIdClientAndIdTypeAndIdAccountAndNumberCuent(idClient, idType, idAccount, numberCuent).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Mono<CreditDto> getCreditByNumberCuent(String numberCuent) {
    return this.creditRepositoryMongodb.findByNumberCuent(numberCuent).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Flux<CreditDto> getCreditByIdClient(String idClient) {
    return this.creditRepositoryMongodb.findByIdClient(idClient);
  }

  @Override
  public Flux<CreditDto> getCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idAccount, String dateStart, String dateEnd) {
    return this.creditRepositoryMongodb.findByIdClientAndIdTypeAndIdAccountAndCreationDateBetween(idClient, idType, idAccount, dateStart, dateEnd);
  }

  @Override
  public Flux<CreditDto> getCreditByNumberCard(String NumberCard) {
    return this.creditRepositoryMongodb.findByNumberCard(NumberCard).defaultIfEmpty(new CreditDto());
  }

  @Override
  public Mono<CreditDto> getCreditByNumberCardAndCategory(String numberCard, int category) {
    return this.creditRepositoryMongodb.findByNumberCardAndCategory(numberCard, category);
  }



}

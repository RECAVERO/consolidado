package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.TypeRepository;
import com.nttdata.domain.models.TypeDto;
import com.nttdata.infraestructure.mongodb.TypeRepositoryMongodb;
import com.nttdata.util.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public class TypeRepositoryImpl implements TypeRepository {
  private final TypeRepositoryMongodb typeRepositoryMongodb;

  public TypeRepositoryImpl(TypeRepositoryMongodb typeRepositoryMongodb) {
    this.typeRepositoryMongodb = typeRepositoryMongodb;
  }

  @Override
  public Flux<TypeDto> getListType() {
    return this.typeRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<TypeDto> saveType(Mono<TypeDto> typeDto) {
    return typeDto.map(Convert::dtoToEntity)
        .flatMap(this.typeRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<TypeDto> updateType(Mono<TypeDto> typeDto, String id) {
    return  this.typeRepositoryMongodb.findById(id)
        .flatMap(p -> typeDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.typeRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<TypeDto> getByIdType(String id) {
    return this.typeRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new TypeDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.typeRepositoryMongodb.deleteById(id);
  }

  @Override
  public Mono<TypeDto> findByIdType(String idType) {
    return this.typeRepositoryMongodb.findByIdType(idType).defaultIfEmpty(new TypeDto());
  }
}

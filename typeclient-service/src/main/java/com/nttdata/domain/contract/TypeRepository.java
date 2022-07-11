package com.nttdata.domain.contract;

import com.nttdata.domain.models.TypeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeRepository {
  Flux<TypeDto> getListType();
  Mono<TypeDto> saveType(Mono<TypeDto> typeDto);
  Mono<TypeDto> updateType(Mono<TypeDto> typeDto, String id);
  Mono<TypeDto> getByIdType(String id);
  Mono<Void> deleteById(String id);

  Mono<TypeDto> findByIdType(String idType);
}

package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.TypeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TypeService {
  Flux<TypeDto> getListType();
  Mono<TypeDto> saveType(Mono<TypeDto> typeDto);
  Mono<TypeDto> updateType(Mono<TypeDto> typeDto, String id);
  Mono<TypeDto> getByIdType(String id);
  Mono<Void> deleteById(String id);
  Mono<TypeDto> findByIdType(String idType);
}

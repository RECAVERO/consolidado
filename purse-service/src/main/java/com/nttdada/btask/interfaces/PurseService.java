package com.nttdada.btask.interfaces;

import com.nttdada.domain.models.PurseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurseService {
  Flux<PurseDto> getListPurse();
  Mono<PurseDto> savePurse(Mono<PurseDto> purseDto);
  Mono<PurseDto> updatePurse(Mono<PurseDto> purseDto, String id);
  Mono<PurseDto> getByIdPurse(String id);
  Mono<Void> deleteById(String id);

  Mono<PurseDto> findByNumberIdentity(String numberIdentity);

  Mono<PurseDto> findByNumberCell(String numberCell);
  Mono<PurseDto> findByEmail(String email);
}

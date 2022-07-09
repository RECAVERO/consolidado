package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.TypeService;
import com.nttdata.domain.models.TypeDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class TypeServiceImpl implements TypeService {
  private final TypeService typeService;

  public TypeServiceImpl(TypeService typeService) {
    this.typeService = typeService;
  }

  @Override
  public Flux<TypeDto> getListType() {
    return this.typeService.getListType();
  }

  @Override
  public Mono<TypeDto> saveType(Mono<TypeDto> typeDto) {
    return this.typeService.saveType(typeDto);
  }

  @Override
  public Mono<TypeDto> updateType(Mono<TypeDto> typeDto, String id) {
    return this.typeService.updateType(typeDto, id);
  }

  @Override
  public Mono<TypeDto> getByIdType(String id) {
    return this.typeService.getByIdType(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.typeService.deleteById(id);
  }
}

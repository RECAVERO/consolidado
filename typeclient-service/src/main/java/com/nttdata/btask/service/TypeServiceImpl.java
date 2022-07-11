package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.TypeService;
import com.nttdata.domain.contract.TypeRepository;
import com.nttdata.domain.models.TypeDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class TypeServiceImpl implements TypeService {
  private final TypeRepository typeRepository;

  public TypeServiceImpl(TypeRepository typeRepository) {
    this.typeRepository = typeRepository;
  }


  @Override
  public Flux<TypeDto> getListType() {
    return this.typeRepository.getListType();
  }

  @Override
  public Mono<TypeDto> saveType(Mono<TypeDto> typeDto) {
    return this.typeRepository.saveType(typeDto);
  }

  @Override
  public Mono<TypeDto> updateType(Mono<TypeDto> typeDto, String id) {
    return this.typeRepository.updateType(typeDto, id);
  }

  @Override
  public Mono<TypeDto> getByIdType(String id) {
    return this.typeRepository.getByIdType(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.typeRepository.deleteById(id);
  }

  @Override
  public Mono<TypeDto> findByIdType(String idType) {
    return this.typeRepository.findByIdType(idType);
  }
}

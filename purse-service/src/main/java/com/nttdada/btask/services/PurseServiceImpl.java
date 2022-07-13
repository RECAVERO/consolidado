package com.nttdada.btask.services;

import com.nttdada.btask.interfaces.PurseService;
import com.nttdada.domain.contract.PurseRepository;
import com.nttdada.domain.models.PurseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class PurseServiceImpl implements PurseService {
  private final PurseRepository purseRepository;

  public PurseServiceImpl(PurseRepository purseRepository) {
    this.purseRepository = purseRepository;
  }

  @Override
  public Flux<PurseDto> getListPurse() {
    return this.purseRepository.getListPurse();
  }

  @Override
  public Mono<PurseDto> savePurse(Mono<PurseDto> purseDto) {
    return this.purseRepository.savePurse(purseDto);
  }

  @Override
  public Mono<PurseDto> updatePurse(Mono<PurseDto> purseDto, String id) {
    return this.purseRepository.updatePurse(purseDto, id);
  }

  @Override
  public Mono<PurseDto> getByIdPurse(String id) {
    return this.purseRepository.getByIdPurse(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.purseRepository.deleteById(id);
  }

  @Override
  public Mono<PurseDto> findByNumberIdentity(String numberIdentity) {
    return this.purseRepository.findByNumberIdentity(numberIdentity);
  }

  @Override
  public Mono<PurseDto> findByNumberCell(String numberCell) {
    return this.purseRepository.findByNumberCell(numberCell);
  }

  @Override
  public Mono<PurseDto> findByEmail(String email) {
    return this.purseRepository.findByEmail(email);
  }
}

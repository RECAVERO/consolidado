package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.ClientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
  Flux<ClientDto> getListClient();
  Mono<ClientDto> saveClient(Mono<ClientDto> clientDto);
  Mono<ClientDto> updateClient(Mono<ClientDto> clientDto, String id);
  Mono<ClientDto> getByIdClient(String id);
  Mono<Void> deleteById(String id);
  Mono<ClientDto> findByIdClient(String idClient);
}

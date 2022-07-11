package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.ClientRepository;
import com.nttdata.domain.models.ClientDto;
import com.nttdata.infraestructure.mongodb.ClientRepositoryMongodb;
import com.nttdata.util.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
  private final ClientRepositoryMongodb clientRepositoryMongodb;

  public ClientRepositoryImpl(ClientRepositoryMongodb clientRepositoryMongodb) {
    this.clientRepositoryMongodb = clientRepositoryMongodb;
  }

  @Override
  public Flux<ClientDto> getListClient() {
    return this.clientRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<ClientDto> saveClient(Mono<ClientDto> clientDto) {
    return clientDto.map(Convert::dtoToEntity)
        .flatMap(this.clientRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<ClientDto> updateClient(Mono<ClientDto> clientDto, String id) {
    return  this.clientRepositoryMongodb.findById(id)
        .flatMap(p -> clientDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.clientRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<ClientDto> getByIdClient(String id) {
    return this.clientRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new ClientDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.clientRepositoryMongodb.deleteById(id);
  }

  @Override
  public Mono<ClientDto> findByIdClient(String idClient) {
    return this.clientRepositoryMongodb.findByIdClient(idClient).defaultIfEmpty(new ClientDto());
  }
}

package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.ClientService;
import com.nttdata.domain.contract.ClientRepository;
import com.nttdata.domain.models.ClientDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class ClientServiceImpl implements ClientService {
  private final ClientRepository clientRepository;

  public ClientServiceImpl(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Flux<ClientDto> getListClient() {
    return this.clientRepository.getListClient();
  }

  @Override
  public Mono<ClientDto> saveClient(Mono<ClientDto> clientDto) {
    return this.clientRepository.saveClient(clientDto);
  }

  @Override
  public Mono<ClientDto> updateClient(Mono<ClientDto> clientDto, String id) {
    return this.clientRepository.updateClient(clientDto, id);
  }

  @Override
  public Mono<ClientDto> getByIdClient(String id) {
    return this.clientRepository.getByIdClient(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.clientRepository.deleteById(id);
  }

  @Override
  public Mono<ClientDto> findByIdClient(String idClient) {
    return this.clientRepository.findByIdClient(idClient);
  }
}

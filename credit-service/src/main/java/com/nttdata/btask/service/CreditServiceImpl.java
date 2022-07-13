package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.CreditService;
import com.nttdata.domain.contract.CreditRepository;
import com.nttdata.domain.models.AccountDto;
import com.nttdata.domain.models.ClientDto;
import com.nttdata.domain.models.CreditDto;
import com.nttdata.domain.models.TypeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class CreditServiceImpl implements CreditService {
  private final CreditRepository creditRepository;
  private final WebClient.Builder webClientBuilder;

  public CreditServiceImpl(CreditRepository creditRepository, WebClient.Builder webClientBuilder) {
    this.creditRepository = creditRepository;
    this.webClientBuilder = webClientBuilder;
  }

  @Override
  public Flux<CreditDto> getListCredit() {
    return this.creditRepository.getListCredit();
  }

  @Override
  public Mono<CreditDto> saveCredit(Mono<CreditDto> creditDto) {
    return this.creditRepository.saveCredit(creditDto);
  }

  @Override
  public Mono<CreditDto> updateCredit(Mono<CreditDto> creditDto, String id) {
    return this.creditRepository.updateCredit(creditDto, id);
  }

  @Override
  public Mono<CreditDto> getByIdCredit(String id) {
    return this.creditRepository.getByIdCredit(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.creditRepository.deleteById(id);
  }




  @Override
  public Mono<ClientDto> getByIdClient(String idClient) {
    return this.webClientBuilder.build()
            .get()
            .uri("http://localhost:5001/client/search/"+ idClient)
            .retrieve()
            .bodyToMono(ClientDto.class);
  }

  @Override
  public Mono<TypeDto> getByIdType(String idType) {
    return this.webClientBuilder.build()
            .get()
            .uri("http://localhost:5002/type/search/"+ idType)
            .retrieve()
            .bodyToMono(TypeDto.class);
  }

  @Override
  public Mono<AccountDto> getByIdAccount(String idAccount) {
    return this.webClientBuilder.build()
            .get()
            .uri("http://localhost:5003/account/search/"+ idAccount)
            .retrieve()
            .bodyToMono(AccountDto.class);
  }


  @Override
  public Mono<CreditDto> getByIdClientAndIdTypeAndIdAccount(String idClient, String idType, String idAccount) {
    return this.creditRepository.getByIdClientAndIdTypeAndIdAccount(idClient, idType, idAccount);
  }

  @Override
  public Mono<CreditDto> getByIdClientAndIdTypeAndIdAccountAndNumberCuent(String idClient, String idType, String idAccount, String numberCuent) {
    return this.creditRepository.getByIdClientAndIdTypeAndIdAccountAndNumberCuent(idClient, idType, idAccount, numberCuent);
  }

  @Override
  public Mono<CreditDto> getCreditByNumberCuent(String numberCuent) {
    return this.creditRepository.getCreditByNumberCuent(numberCuent);
  }

  @Override
  public Mono<ClientDto> saveClient(Mono<ClientDto> clientDto) {
    return clientDto.flatMap(client->{
      return this.webClientBuilder.build()
              .post()
              .uri("http://localhost:5001/client")
              .body(Mono.just(client),ClientDto.class)
              .retrieve()
              .bodyToMono(ClientDto.class);
    });

  }

  @Override
  public Flux<CreditDto> getCreditByIdClient(String idClient) {
    return this.creditRepository.getCreditByIdClient(idClient);
  }

  @Override
  public Flux<CreditDto> getCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idAccount, String dateStart, String dateEnd) {
    return this.creditRepository.getCreditByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient, idType, idAccount, dateStart, dateEnd);
  }

  @Override
  public Flux<CreditDto> getCreditByNumberCard(String NumberCard) {
    return this.creditRepository.getCreditByNumberCard(NumberCard);
  }

  @Override
  public Mono<CreditDto> getCreditByNumberCardAndCategory(String numberCard, int category) {
    return this.creditRepository.getCreditByNumberCardAndCategory(numberCard, category);
  }

}

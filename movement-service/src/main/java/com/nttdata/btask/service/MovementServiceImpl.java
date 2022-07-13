package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.MovementService;
import com.nttdata.domain.contract.MovementRepository;
import com.nttdata.domain.models.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;
    private final WebClient.Builder webClientBuilder;

    public MovementServiceImpl(MovementRepository movementRepository, WebClient.Builder webClientBuilder) {
        this.movementRepository = movementRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Flux<MovementDto> getListMovement() {
        return this.movementRepository.getListMovement();
    }

    @Override
    public Mono<MovementDto> saveMovement(Mono<MovementDto> movementDto) {
        return this.movementRepository.saveMovement(movementDto);
    }

    @Override
    public Mono<MovementDto> updateMovement(Mono<MovementDto> movementDto, String id) {
        return this.movementRepository.updateMovement(movementDto, id);
    }

    @Override
    public Mono<MovementDto> getByIdMovement(String id) {
        return this.movementRepository.getByIdMovement(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return this.movementRepository.deleteById(id);
    }

    @Override
    public Mono<CreditDto> getCredit(String idClient, String idType, String idAccount, String numberCuent) {
        return this.webClientBuilder.build()
                .get()
                .uri("http://localhost:5004/credit/product/"+ idClient + "/" + idType + "/" + idAccount + "/" + numberCuent )
                .retrieve()
                .bodyToMono(CreditDto.class);
    }

    @Override
    public Mono<CreditDto> updateCreditDeposit(Mono<CreditDto> creditDto) {
        return  creditDto.flatMap(c->{
            return this.webClientBuilder.build()
                    .post()
                    .uri("http://localhost:5004/credit/deposit")
                    .body(Mono.just(c), CreditDto.class)
                    .retrieve()
                    .bodyToMono(CreditDto.class);
        });
    }

    @Override
    public Mono<CreditDto> updateCreditWithdrawal(Mono<CreditDto> creditDto) {
        return  creditDto.flatMap(c->{
            return this.webClientBuilder.build()
                    .post()
                    .uri("http://localhost:5004/credit/withdrawal")
                    .body(Mono.just(c), CreditDto.class)
                    .retrieve()
                    .bodyToMono(CreditDto.class);
        });
    }

    @Override
    public Flux<MovementDto> getListMovementByIdClientAndNumberCuent(String idClient, String numberCuent) {
        return this.movementRepository.getListMovementByIdClientAndNumberCuent(idClient, numberCuent);
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
    public Flux<MovementDto> getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(String idClient, String idType, String idProduct, String dateStart, String dateEnd) {
        return this.movementRepository.getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient, idType, idProduct, dateStart, dateEnd);
    }

    @Override
    public Flux<CreditDto> getListCreditByIdClientAndIdTypeAndIdAccountAndCreationDateBetween(String idClient, String idType, String idAccount, String dateStart, String dateEnd) {
        return webClientBuilder.build()
            .get().uri("http://localhost:5004/credit/products/"+ idClient +"/"+idType +"/"+idAccount +"/"+ dateStart +"/"+ dateEnd)
            .retrieve()
            .bodyToFlux(CreditDto.class);
    }
    @Override
    public Mono<CreditDto> getCreditByIdNumberCard(String numberCuent) {
        return webClientBuilder.build()
            .get().uri("http://localhost:5004/credit/search/card/"+ numberCuent)
            .retrieve()
            .bodyToMono(CreditDto.class);
    }

    @Override
    public Flux<MovementDto> getListMovementByIdNumberCard(String numberCard) {
        return this.movementRepository.getListMovementByIdNumberCard(numberCard);
    }

}

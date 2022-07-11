package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.MovementService;
import com.nttdata.domain.models.CreditDto;
import com.nttdata.domain.models.MovementDto;
import com.nttdata.domain.models.ResponseDto;
import com.nttdata.domain.models.ResponseListDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/movement")
public class MovementController {
    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping
    public Flux<MovementDto> getListClient(){
        return this.movementService.getListMovement();
    }
    @PostMapping
    public Mono<MovementDto> saveClient(@RequestBody Mono<MovementDto> movementDto){
        return movementDto.flatMap(movement->{
            movement.setCreationDate(this.getDateNow());
            movement.setUpdatedDate(this.getDateNow());
            movement.setActive(1);
            return this.movementService.saveMovement(Mono.just(movement));
        });
    }


    @PutMapping("/{id}")
    public Mono<ResponseDto> updateClient(@RequestBody Mono<MovementDto> movementDto, @PathVariable String id){
        ResponseDto responseDto=new ResponseDto();
        return movementDto.flatMap(cre->{
            return this.movementService.getByIdMovement(id).flatMap(credit->{
                if(credit.getId()==null){
                    responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                    responseDto.setMessage("Credit not Exits");
                    return Mono.just(responseDto);
                }else{
                    responseDto.setStatus(HttpStatus.OK.toString());
                    responseDto.setMessage("Credit Updated!");
                    credit.setIdClient(cre.getIdClient());
                    credit.setIdType(cre.getIdType());
                    credit.setIdAccount(cre.getIdAccount());
                    credit.setNumberCuent(cre.getNumberCuent());
                    credit.setNumberCard(cre.getNumberCard());
                    credit.setAmount(cre.getAmount());
                    credit.setStatus(cre.getStatus());
                    credit.setCategory(cre.getCategory());
                    credit.setUpdatedDate(this.getDateNow());

                    return this.movementService.updateMovement(Mono.just(credit), id).flatMap(m->{
                        responseDto.setMovement(m);
                        return Mono.just(responseDto);
                    });
                }
            });
        });
    }

    @GetMapping("/{id}")
    public Mono<MovementDto> getClientById(@PathVariable String id){
        return this.movementService.getByIdMovement(id);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseDto> deleteClientById(@PathVariable String id){
        ResponseDto responseDto=new ResponseDto();

        return this.movementService.getByIdMovement(id).flatMap(credit->{
            if(credit.getId()==null){
                responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                responseDto.setMessage("Client not Exits");
                return Mono.just(responseDto);
            }else{


                return this.movementService.deleteById(id).flatMap(c->{
                    responseDto.setStatus(HttpStatus.OK.toString());
                    responseDto.setMessage("Client Deleted!");
                    if(c == null){
                        return Mono.just(responseDto);
                    }else{
                        return Mono.just(responseDto);
                    }
                });
            }
        });


    }
    private String getDateNow(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date).toString();
    }

    @PostMapping("/deposit")
    public Mono<ResponseDto> depositMovement(@RequestBody Mono<MovementDto> movementDto){
        ResponseDto responseDto = new ResponseDto();
        return movementDto.flatMap(movement->{
            return this.movementService.getCredit(movement.getIdClient(), movement.getIdType(), movement.getIdAccount(), movement.getNumberCuent()).flatMap(credit->{
                if(credit.getId() == null){
                    responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                    responseDto.setMessage("No exits Number Cuent");
                    return Mono.just(responseDto);
                }else{
                    credit.setBalance(movement.getAmount());
                    credit.setUpdatedDate(this.getDateNow());
                    return this.movementService.updateCreditDeposit(Mono.just(credit)).flatMap(cr->{
                        movement.setOperation("Deposit");
                        movement.setCreationDate(this.getDateNow());
                        movement.setUpdatedDate(this.getDateNow());
                        movement.setActive(1);
                        return this.movementService.saveMovement(Mono.just(movement)).flatMap(mov->{
                            responseDto.setStatus(HttpStatus.CREATED.toString());
                            responseDto.setMessage("Created Movement de Deposit");
                            responseDto.setMovement(mov);
                            return Mono.just(responseDto);
                        });
                    });

                }
            });
        });
    }

    @PostMapping("/withdrawal")
    public Mono<ResponseDto> withdrawalMovement(@RequestBody Mono<MovementDto> movementDto){
        ResponseDto responseDto = new ResponseDto();
        return movementDto.flatMap(movement->{
            return this.movementService.getCredit(movement.getIdClient(), movement.getIdType(), movement.getIdAccount(), movement.getNumberCuent()).flatMap(credit->{
                if(credit.getId() == null){
                    responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                    responseDto.setMessage("No exits Number Cuent");
                    return Mono.just(responseDto);
                }else{

                    if(credit.getBalance() >= movement.getAmount()){
                        credit.setBalance(movement.getAmount());
                        credit.setUpdatedDate(this.getDateNow());
                        return this.movementService.updateCreditWithdrawal(Mono.just(credit)).flatMap(cr->{
                            movement.setOperation("withdrawal");
                            movement.setCreationDate(this.getDateNow());
                            movement.setUpdatedDate(this.getDateNow());
                            movement.setActive(1);
                            return this.movementService.saveMovement(Mono.just(movement)).flatMap(mov->{
                                responseDto.setStatus(HttpStatus.CREATED.toString());
                                responseDto.setMessage("Created Movement de Deposit");
                                responseDto.setMovement(mov);
                                return Mono.just(responseDto);
                            });
                        });
                    }else{
                        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                        responseDto.setMessage("you don't have enough balance");
                        return Mono.just(responseDto);
                    }


                }
            });
        });
    }

    @GetMapping("/report/{idClient}/{numberCuent}")
    public Flux<MovementDto> getListMovementByIdClientWithNumberCuent(@PathVariable String idClient, @PathVariable String numberCuent){

        return this.movementService.getListMovementByIdClientAndNumberCuent(idClient, numberCuent).flatMap(c->{
            return this.movementService.getByIdClient(c.getIdClient()).flatMap(client->{
                return this.movementService.getByIdType(c.getIdType()).flatMap(type->{
                    return this.movementService.getByIdAccount(c.getIdAccount()).flatMap(account->{
                        c.setIdClient(client.getNames());
                        c.setIdType(type.getTypeClient());
                        c.setIdAccount(account.getTypeAccount());
                        return Mono.just(c);
                    });
                });
            });
        });
    }


}

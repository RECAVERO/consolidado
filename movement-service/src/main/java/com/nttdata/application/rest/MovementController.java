package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.MovementService;
import com.nttdata.domain.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
                        credit.setIdMovementNumberCard(0);
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

    @GetMapping("/products/generalReport/{idClient}/{idType}/{idAccount}/{dateStart}/{dateEnd}")
    public Mono<DatasourceDto> findByIdClientAndIdTypeAndIdProductAndCreationDateBetween(@PathVariable String idClient,
                                                                                         @PathVariable String idType,
                                                                                         @PathVariable String idAccount,
                                                                                         @PathVariable String dateStart,
                                                                                         @PathVariable String dateEnd){

        DatasourceDto datasourceDto=new DatasourceDto();
        List<MovementDto> listMovemet=new ArrayList<>();
        return this.movementService.getByIdClient(idClient).flatMap(client->{
            if(client.getId() == null){
                datasourceDto.setStatus(HttpStatus.NOT_FOUND.toString());
                datasourceDto.setMsg("Not exits Client");
                return Mono.just(datasourceDto);
            }else{
                return this.movementService.getByIdType(idType).flatMap(type->{
                    if(type.getId() == null)
                    {
                        datasourceDto.setStatus(HttpStatus.NOT_FOUND.toString());
                        datasourceDto.setMsg("Not exits type Client");
                        return Mono.just(datasourceDto);
                    }else
                    {
                        return this.movementService.getByIdAccount(idAccount).flatMap(account->{
                            if(account.getId() == null)
                            {
                                datasourceDto.setStatus(HttpStatus.NOT_FOUND.toString());
                                datasourceDto.setMsg("Not exits Account Client");
                                return Mono.just(datasourceDto);
                            }else
                            {
                                datasourceDto.setClient(client);
                                datasourceDto.setType(type);
                                datasourceDto.setAccount(account);
                                Mono<List<MovementDto>> flux= movementService.getListMovementByIdClientAndIdTypeAndIdProductAndCreationDateBetween(idClient, idType, idAccount, dateStart, dateEnd).collectList();
                                return flux.flatMap(lista->{
                                    lista.forEach((v)->{
                                        MovementDto cv=new MovementDto();
                                        cv.setId(v.getId());
                                        cv.setOperation(v.getOperation());
                                        cv.setIdClient(v.getIdClient());
                                        cv.setIdType(v.getIdType());
                                        cv.setIdAccount(v.getIdAccount());
                                        cv.setNumberCuent(v.getNumberCuent());
                                        cv.setAmount(v.getAmount());
                                        cv.setUpdatedDate(v.getUpdatedDate());
                                        cv.setCreationDate(v.getCreationDate());
                                        cv.setStatus(v.getStatus());
                                        cv.setCategory(v.getCategory());
                                        cv.setActive(v.getActive());
                                        listMovemet.add(cv);
                                    });
                                    datasourceDto.setMovement(listMovemet);

                                    Mono<List<CreditDto>> fluxCredit=  this.movementService.getListCreditByIdClientAndIdTypeAndIdAccountAndCreationDateBetween(idClient,idType,idAccount,dateStart,dateEnd).collectList();
                                    List<CreditDto> listCredit=new ArrayList<>();
                                    return fluxCredit.flatMap(listaCredit->{
                                        listaCredit.forEach((v)->{
                                            CreditDto cv=new CreditDto();
                                            cv.setId(v.getId());
                                            cv.setIdClient(v.getIdClient());
                                            cv.setIdType(v.getIdType());
                                            cv.setIdAccount(v.getIdAccount());
                                            cv.setNumberCuent(v.getNumberCuent());
                                            cv.setBalance(v.getBalance());
                                            cv.setUpdatedDate(v.getUpdatedDate());
                                            cv.setCreationDate(v.getCreationDate());
                                            cv.setStatus(v.getStatus());
                                            cv.setCategory(v.getCategory());
                                            cv.setActive(v.getActive());
                                            listCredit.add(cv);
                                        });
                                        datasourceDto.setCredit(listCredit);
                                        return Mono.just(datasourceDto);
                                    });
                                });
                            }
                        });
                    }

                });
            }
        });

    }


    @GetMapping("/prueba/{numberCard}")
    public Mono<CreditDto> xxx(@PathVariable String numberCard){
        return this.movementService.getCreditByIdNumberCard(numberCard);
    }

    @PostMapping("/payment")
    public Mono<ResponseDto> paymentMovement(@RequestBody Mono<MovementDto> movementDto){
        ResponseDto responseDto = new ResponseDto();
        return movementDto.flatMap(movement->{
            return this.movementService.getCreditByIdNumberCard(movement.getNumberCuent()).flatMap(credit->{
                if(credit.getId() == null){
                    responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                    responseDto.setMessage("Not exits number card");
                    return Mono.just(responseDto);
                }else{
                    System.out.println(movement.getAmount());
                    if(movement.getAmount() ==  0.0 || movement.getAmount() < 0){
                        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                        responseDto.setMessage("the amount must be greater than 0");
                        return Mono.just(responseDto);
                    }else{
                        if(credit.getBalance() >= movement.getAmount()){

                            credit.setIdMovementNumberCard(1);
                            credit.setBalance(movement.getAmount());
                            credit.setUpdatedDate(this.getDateNow());
                            return this.movementService.updateCreditWithdrawal(Mono.just(credit)).flatMap(cr->{
                                movement.setOperation("withdrawal");
                                movement.setIdMovementNumberCard(cr.getIdMovementNumberCard());
                                movement.setIdClient(credit.getIdClient());
                                movement.setIdType(credit.getIdType());
                                movement.setIdAccount(credit.getIdAccount());
                                movement.setNumberCuent(credit.getNumberCuent());
                                movement.setCategory(credit.getCategory());
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

                }
            });
        });
    }

    @GetMapping("/report/{numberCard}")
    public Mono<ResponseListDto> getMovementByNumberCard(@PathVariable String numberCard){
        ResponseListDto responseListDto=new ResponseListDto();
        List<MovementDto> lista=new ArrayList<>();
        Mono<List<MovementDto>> flux=this.movementService.getListMovementByIdNumberCard(numberCard).collectList();

        return flux.flatMap(f->{
            f.forEach((v)->{
                if(v.getIdMovementNumberCard() < 11 ){
                    lista.add(v);
                }

            });
            responseListDto.setStatus(HttpStatus.OK.toString());
            responseListDto.setMessage("List de Ten register");
            responseListDto.setMovement(lista);
            return Mono.just(responseListDto);
        });


    }

}

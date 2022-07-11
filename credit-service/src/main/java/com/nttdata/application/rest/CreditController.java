package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.CreditService;
import com.nttdata.domain.models.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/credit")
public class CreditController {
  private final CreditService creditService;

  public CreditController(CreditService creditService) {
    this.creditService = creditService;
  }

  @GetMapping
  public Flux<CreditDto> getListCredit(){
    return this.creditService.getListCredit();
  }
  @PostMapping
  public Mono<ResponseDto> saveCredit(@RequestBody Mono<RequestDto> requestDto){
    ResponseDto responseDto = new ResponseDto();
    CreditDto creditDto=new CreditDto();
    ClientDto clientDto=new ClientDto();
    return requestDto.flatMap(credit->{
      return this.creditService.getByIdClient(credit.getIdClient()).flatMap(client->{
        creditDto.setIdClient(credit.getIdClient());
        creditDto.setIdType(credit.getIdType());
        creditDto.setIdAccount(credit.getIdAccount());
        if(client.getIdClient()==null){
          if(credit.getIdAccount().equals("pro04") || credit.getIdAccount().equals("pro05") || credit.getIdAccount().equals("pro06")){
            if(credit.getDni() == null){
              responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
              responseDto.setMessage("Campos obligatorios names, Dni ");
              return Mono.just(responseDto);
            }else{
              clientDto.setIdClient(credit.getIdClient());
              clientDto.setDni(credit.getDni());
              clientDto.setNames(credit.getNames());
              clientDto.setEmail(credit.getEmail());
              return this.creditService.saveClient(Mono.just(clientDto)).flatMap(cl->{
                return this.saveCreditAccount(creditDto, responseDto);
              });
            }

          }else{
            responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
            responseDto.setMessage("Client not exists");
            System.out.println(client);
            return Mono.just(responseDto);
          }
        }else{
            return this.creditService.getByIdType(credit.getIdType()).flatMap(type->{
              if(type.getIdType() == null){
                responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                responseDto.setMessage("Type Client not exists");
                System.out.println(type);
                return Mono.just(responseDto);
              }else{
                if(type.getIdType().equals("typ01")){
                  System.out.println(type.getIdType());
                  System.out.println(credit);
                  if(credit.getIdAccount().equals("pro01") || credit.getIdAccount().equals("pro02") || credit.getIdAccount().equals("pro03")){
                    System.out.println(credit.getIdAccount());
                    return this.creditService.getByIdAccount(credit.getIdAccount()).flatMap(account->{
                      System.out.println(account.getIdAccount() + " " + type.getIdType() + " " + client.getIdClient());
                      return this.creditService.getByIdClientAndIdTypeAndIdAccount(client.getIdClient(), type.getIdType(), account.getIdAccount()).flatMap(product->{
                        if(product.getId() == null){
                          return this.saveCreditAccount(creditDto, responseDto);
                        }else{
                          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                          responseDto.setMessage("client type cannot create two products");
                          return Mono.just(responseDto);
                        }
                      });
                    });
                  }else{
                    responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                    responseDto.setMessage("client type cannot create those products");
                    return Mono.just(responseDto);
                  }

                }else if(type.getIdType().equals("typ02")){
                  System.out.println(type.getIdType());
                  if(credit.getIdAccount().equals("pro01") || credit.getIdAccount().equals("pro03")){
                    System.out.println(credit.getIdAccount());
                    responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                    responseDto.setMessage("client type cannot create those products : Cuenta de ahorro, plazo fijo");
                    return Mono.just(responseDto);
                  }else{
                    return this.saveCreditAccount(creditDto, responseDto);
                  }
                }else{
                  responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                  responseDto.setMessage("client type not exits");
                  return Mono.just(responseDto);
                }
              }
            });
        }
      });
    });
  }

  private Mono<ResponseDto> saveCreditAccount(CreditDto credit, ResponseDto responseDto){
    credit.setBalance(0);
    credit.setCategory(0);
    credit.setStatus(0);
    credit.setNumberCuent(UUID.randomUUID().toString());
    credit.setCreationDate(this.getDateNow());
    credit.setUpdatedDate(this.getDateNow());
    credit.setActive(1);
    return this.creditService.saveCredit(Mono.just(credit)).flatMap(cre->{
      responseDto.setStatus(HttpStatus.CREATED.toString());
      responseDto.setMessage("Created Account");
      responseDto.setCredit(cre);
      return Mono.just(responseDto);
    });
  }
  //@CircuitBreaker(name = "DepositCB", fallbackMethod = "fallBackPostCreditDeposit")
  @PostMapping("/prueba")
  public Mono<AccountDto> prueba(@RequestBody Mono<AccountDto> clientDtoMono){
    return clientDtoMono.flatMap(client->{
      return this.creditService.getByIdAccount(client.getIdAccount());
    });
  }

  public Mono<AccountDto> fallBackPostCreditDeposit(@RequestBody Mono<AccountDto> clientDtoMono,
                                                                 RuntimeException e) {
    return clientDtoMono.flatMap(c->{
      return Mono.just(c);
    });
  }

  @PutMapping("/{id}")
  public Mono<ResponseDto> updateCredit(@RequestBody Mono<CreditDto> creditDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return creditDto.flatMap(cre->{
      return this.creditService.getByIdCredit(id).flatMap(credit->{
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
          credit.setBalance(cre.getBalance());
          credit.setStatus(cre.getStatus());
          credit.setCategory(cre.getCategory());
          credit.setUpdatedDate(this.getDateNow());

          return this.creditService.updateCredit(Mono.just(credit), id).flatMap(c->{
            responseDto.setCredit(c);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<CreditDto> getCreditById(@PathVariable String id){
    return this.creditService.getByIdCredit(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deleteCreditById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.creditService.getByIdCredit(id).flatMap(credit->{
      if(credit.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Client not Exits");
        return Mono.just(responseDto);
      }else{


        return this.creditService.deleteById(id).flatMap(c->{
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

  @GetMapping("/product/{numberCuent}")
  public Mono<CreditDto> getCreditByNumberCuent(@PathVariable String numberCuent){
    return this.creditService.getCreditByNumberCuent(numberCuent).flatMap(credit->{
      return this.creditService.getByIdClient(credit.getIdClient()).flatMap(client->{
        credit.setIdClient(client.getNames());
        return this.creditService.getByIdType(credit.getIdType()).flatMap(type->{
          credit.setIdType(type.getTypeClient());
          return this.creditService.getByIdAccount(credit.getIdAccount()).flatMap(account->{
            credit.setIdAccount(account.getTypeAccount());
            return Mono.just(credit);
          });
        });
      });
    });
  }

  @GetMapping("/product/{idClient}/{idType}/{idAccount}/{numberCuent}")
  public Mono<CreditDto> getExistCredit(@PathVariable String idClient,
                                        @PathVariable String idType,
                                        @PathVariable String idAccount,
                                        @PathVariable String numberCuent ){
    return this.creditService.getByIdClientAndIdTypeAndIdAccountAndNumberCuent(idClient, idType, idAccount, numberCuent);

  }



  @PostMapping("/deposit")
  public Mono<CreditDto> updateCreditDeposit(@RequestBody Mono<CreditDto> creditDto){
    return creditDto.flatMap(credit->{
      return this.creditService.getByIdCredit(credit.getId()).flatMap(c->{
        c.setUpdatedDate(this.getDateNow());
        c.setBalance(c.getBalance() + credit.getBalance());
        return this.creditService.updateCredit(Mono.just(c), c.getId());
      });
    });
  }

  @PostMapping("/withdrawal")
  public Mono<CreditDto> updateCreditWithdrawal(@RequestBody Mono<CreditDto> creditDto){
    return creditDto.flatMap(credit->{
      return this.creditService.getByIdCredit(credit.getId()).flatMap(c->{
        c.setUpdatedDate(this.getDateNow());
        c.setBalance(c.getBalance() - credit.getBalance());
        return this.creditService.updateCredit(Mono.just(c), c.getId());
      });
    });
  }

}

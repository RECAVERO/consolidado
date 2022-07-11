package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.AccountService;
import com.nttdata.domain.models.AccountDto;
import com.nttdata.domain.models.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public Flux<AccountDto> getListClient(){
    return this.accountService.getListAccount();
  }
  @PostMapping
  public Mono<AccountDto> saveClient(@RequestBody Mono<AccountDto> accountDto){
    return accountDto.flatMap(account->{
      account.setCreationDate(this.getDateNow());
      account.setUpdatedDate(this.getDateNow());
      account.setActive(1);
      return this.accountService.saveAccount(Mono.just(account));
    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updateClient(@RequestBody Mono<AccountDto> clientDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return clientDto.flatMap(acc->{
      return this.accountService.getByIdAccount(id).flatMap(account->{
        if(account.getId()==null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Client not Exits");
          return Mono.just(responseDto);
        }else{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Client Updated!");
          account.setTypeAccount(acc.getTypeAccount());
          account.setUpdatedDate(this.getDateNow());

          return this.accountService.updateAccount(Mono.just(account), id).flatMap(ac->{
            responseDto.setAccount(ac);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<AccountDto> getClientById(@PathVariable String id){
    return this.accountService.getByIdAccount(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deleteClientById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.accountService.getByIdAccount(id).flatMap(cli->{
      if(cli.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Account not Exits");
        return Mono.just(responseDto);
      }else{


        return this.accountService.deleteById(id).flatMap(c->{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Account Deleted!");
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

  @GetMapping("/search/{idAccount}")
  public Mono<AccountDto> getByIdAccount(@PathVariable String idAccount){
    return this.accountService.getByIdProduct(idAccount);
  }

}

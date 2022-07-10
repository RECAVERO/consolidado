package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.CreditService;
import com.nttdata.domain.models.CreditDto;
import com.nttdata.domain.models.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/credit")
public class CreditController {
  private final CreditService creditService;

  public CreditController(CreditService creditService) {
    this.creditService = creditService;
  }

  @GetMapping
  public Flux<CreditDto> getListClient(){
    return this.creditService.getListCredit();
  }
  @PostMapping
  public Mono<CreditDto> saveClient(@RequestBody Mono<CreditDto> creditDto){
    return creditDto.flatMap(credit->{
      credit.setCreationDate(this.getDateNow());
      credit.setUpdatedDate(this.getDateNow());
      credit.setActive(1);
      return this.creditService.saveCredit(Mono.just(credit));
    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updateClient(@RequestBody Mono<CreditDto> clientDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return clientDto.flatMap(cre->{
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
          credit.setIdProduct(cre.getIdProduct());
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
  public Mono<CreditDto> getClientById(@PathVariable String id){
    return this.creditService.getByIdCredit(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deleteClientById(@PathVariable String id){
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
}

package com.nttdada.application.rest;

import com.nttdada.btask.interfaces.PurchaseService;
import com.nttdada.domain.models.PurchaseDto;
import com.nttdada.domain.models.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping
public class PurchaseController {
  private final PurchaseService purchaseService;

  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }
  @GetMapping
  public Flux<PurchaseDto> getListPurse(){
    return this.purchaseService.getListPurchase();
  }
  @PostMapping
  public Mono<ResponseDto> savePurse(@RequestBody Mono<PurchaseDto> purseDto){
    ResponseDto responseDto=new ResponseDto();
    return purseDto.flatMap(purse->{
                  return this.purchaseService.savePurchase(Mono.just(purse)).flatMap(x->{
                    responseDto.setStatus(HttpStatus.CREATED.toString());
                    responseDto.setMessage("Purse Created");
                    responseDto.setPurchase(x);
                    return Mono.just(responseDto);
                  });
    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updatePurse(@RequestBody Mono<PurchaseDto> currencyDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return currencyDto.flatMap(currency->{
      return this.purchaseService.getByIdPurchase(id).flatMap(purse->{
        if(purse.getId()==null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Purse not Exits");
          return Mono.just(responseDto);
        }else{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Purse Updated!");
          purse.setNumberCell(currency.getNumberCell());
          purse.setUpdatedDate(this.getDateNow());
          return this.purchaseService.updatePurchase(Mono.just(purse), id).flatMap(c->{
            responseDto.setPurchase(c);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<PurchaseDto> getPurseById(@PathVariable String id){
    return this.purchaseService.getByIdPurchase(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deletePurseById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.purchaseService.getByIdPurchase(id).flatMap(cli->{
      if(cli.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Purse not Exits");
        return Mono.just(responseDto);
      }else{


        return this.purchaseService.deleteById(id).flatMap(c->{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Purse Deleted!");
          return Mono.just(responseDto);
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

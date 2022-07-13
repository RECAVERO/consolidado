package com.nttdada.application.rest;

import com.nttdada.btask.interfaces.CurrencyService;
import com.nttdada.domain.models.CurrencyDto;
import com.nttdada.domain.models.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
  private final CurrencyService currencyService;

  public CurrencyController(CurrencyService currencyService) {
    this.currencyService = currencyService;
  }

  @GetMapping
  public Flux<CurrencyDto> getListClient(){
    return this.currencyService.getListCurrency();
  }
  @PostMapping
  public Mono<ResponseDto> saveClient(@RequestBody Mono<CurrencyDto> currencyDto){
    ResponseDto responseDto=new ResponseDto();
    return currencyDto.flatMap(currency->{
      return this.currencyService.findByIdCurrency(currency.getIdCurrency()).flatMap(c->{
        if(c.getIdCurrency() == null){
          currency.setCreatedDate(this.getDateNow());
          currency.setUpdatedDate(this.getDateNow());
          currency.setActive(1);
          return this.currencyService.saveCurrency(Mono.just(currency)).flatMap(x->{
            responseDto.setStatus(HttpStatus.CREATED.toString());
            responseDto.setMessage("Currency Created");
            responseDto.setCurrency(x);
            return Mono.just(responseDto);
          });
        }else{
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Not Currency Created");
          return Mono.just(responseDto);
        }
      });

    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updateClient(@RequestBody Mono<CurrencyDto> currencyDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return currencyDto.flatMap(currency->{
      return this.currencyService.getByIdCurrency(id).flatMap(curre->{
        if(curre.getId()==null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Client not Exits");
          return Mono.just(responseDto);
        }else{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Client Updated!");
          curre.setPriceSale(currency.getPriceSale());
          curre.setPricePurchase(currency.getPricePurchase());
          curre.setUpdatedDate(this.getDateNow());
          return this.currencyService.updateCurrency(Mono.just(curre), id).flatMap(c->{
            responseDto.setCurrency(c);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<CurrencyDto> getClientById(@PathVariable String id){
    return this.currencyService.getByIdCurrency(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deleteClientById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.currencyService.getByIdCurrency(id).flatMap(cli->{
      if(cli.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Client not Exits");
        return Mono.just(responseDto);
      }else{


        return this.currencyService.deleteById(id).flatMap(c->{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Client Deleted!");
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


  @GetMapping("/search/{idCurrency}")
  public Mono<CurrencyDto> getByIdClient(@PathVariable String idCurrency){
    return this.currencyService.findByIdCurrency(idCurrency);
  }
  @PostMapping("/convert")
  public Mono<ResponseDto> covertCurrency(@RequestBody Mono<CurrencyDto> currencyDto){
    ResponseDto responseDto=new ResponseDto();
    return currencyDto.flatMap(currency->{
      return this.currencyService.findByIdCurrency(currency.getIdCurrency()).flatMap(c->{
        if(c.getId() == null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("No exits type Money");
          return Mono.just(responseDto);
        }else{
          if(c.getIdCurrency().equals("cur02")){
            c.setPriceSale(currency.getAmount() * c.getPriceSale());
            c.setPricePurchase(c.getPricePurchase());
          }else{
            c.setPriceSale(c.getPriceSale());
            c.setPricePurchase(currency.getAmount() * c.getPriceSale());
          }
          responseDto.setStatus(HttpStatus.CREATED.toString());
          responseDto.setMessage("OK");
          responseDto.setCurrency(c);
          return Mono.just(responseDto);
        }

      });

    });
  }

}

package com.nttdada.application.rest;

import com.nttdada.btask.interfaces.PaymentTypeService;
import com.nttdada.domain.models.PaymenttypeDto;
import com.nttdada.domain.models.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/paymenttype")
public class PaymentTypeController {
  private final PaymentTypeService paymentTypeService;

  public PaymentTypeController(PaymentTypeService paymentTypeService) {
    this.paymentTypeService = paymentTypeService;
  }

  @GetMapping
  public Flux<PaymenttypeDto> getListClient(){
    return this.paymentTypeService.getListPaymentType();
  }
  @PostMapping
  public Mono<PaymenttypeDto> saveClient(@RequestBody Mono<PaymenttypeDto> paymenttypeDto){
    return paymenttypeDto.flatMap(paymentType->{
      paymentType.setCreationDate(this.getDateNow());
      paymentType.setUpdatedDate(this.getDateNow());
      paymentType.setActive(1);
      return this.paymentTypeService.savePaymentType(Mono.just(paymentType));
    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updateClient(@RequestBody Mono<PaymenttypeDto> paymenttypeDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return paymenttypeDto.flatMap(type->{
      return this.paymentTypeService.getByIdPaymentType(id).flatMap(paymentTyp->{
        if(paymentTyp.getId()==null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Type Payment Type not Exits");
          return Mono.just(responseDto);
        }else{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Type Payment Updated!");
          paymentTyp.setTypePayment(type.getTypePayment());
          paymentTyp.setUpdatedDate(this.getDateNow());

          return this.paymentTypeService.updatePaymentType(Mono.just(paymentTyp), id).flatMap(t->{
            responseDto.setPaymentType(t);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<PaymenttypeDto> getClientById(@PathVariable String id){
    return this.paymentTypeService.getByIdPaymentType(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deleteClientById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.paymentTypeService.getByIdPaymentType(id).flatMap(cli->{
      if(cli.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Client not Exits");
        return Mono.just(responseDto);
      }else{


        return this.paymentTypeService.deleteById(id).flatMap(c->{
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

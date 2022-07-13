package com.nttdada.application.rest;

import com.nttdada.btask.interfaces.PurseService;
import com.nttdada.domain.models.PurseDto;
import com.nttdada.domain.models.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/purse")
public class PurseController {
  private final PurseService purseService;

  public PurseController(PurseService purseService) {
    this.purseService = purseService;
  }

  @GetMapping
  public Flux<PurseDto> getListPurse(){
    return this.purseService.getListPurse();
  }
  @PostMapping
  public Mono<ResponseDto> savePurse(@RequestBody Mono<PurseDto> purseDto){
    ResponseDto responseDto=new ResponseDto();
    return purseDto.flatMap(purse->{
      return this.purseService.findByNumberIdentity(purse.getNumberIdentity()).flatMap(c->{
        System.out.println(c);
        if(c.getNumberIdentity() == null){

          return this.purseService.findByNumberCell(purse.getNumberCell()).flatMap(ce->{
            if(ce.getId() == null){
              return this.purseService.findByEmail(purse.getEmail()).flatMap(en->{
                System.out.println(en);
                if(en.getId() == null){
                  purse.setNumberPurse(UUID.randomUUID().toString());
                  purse.setBalance(0);
                  purse.setCreationDate(this.getDateNow());
                  purse.setUpdatedDate(this.getDateNow());
                  purse.setActive(1);
                  return this.purseService.savePurse(Mono.just(purse)).flatMap(x->{
                    responseDto.setStatus(HttpStatus.CREATED.toString());
                    responseDto.setMessage("Purse Created");
                    responseDto.setPurse(x);
                    return Mono.just(responseDto);
                  });
                }else{
                  responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
                  responseDto.setMessage("email Used");
                  return Mono.just(responseDto);
                }
              });
            }else{
              responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
              responseDto.setMessage("Cell used");
              return Mono.just(responseDto);


            }
          });



        }else{
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Number Identity Used");
          return Mono.just(responseDto);

        }




      });

    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updatePurse(@RequestBody Mono<PurseDto> currencyDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return currencyDto.flatMap(currency->{
      return this.purseService.getByIdPurse(id).flatMap(purse->{
        if(purse.getId()==null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Purse not Exits");
          return Mono.just(responseDto);
        }else{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Purse Updated!");
          purse.setNumberCell(currency.getNumberCell());
          purse.setEmail(currency.getEmail());
          purse.setUpdatedDate(this.getDateNow());
          return this.purseService.updatePurse(Mono.just(purse), id).flatMap(c->{
            responseDto.setPurse(c);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<PurseDto> getPurseById(@PathVariable String id){
    return this.purseService.getByIdPurse(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deletePurseById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.purseService.getByIdPurse(id).flatMap(cli->{
      if(cli.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Purse not Exits");
        return Mono.just(responseDto);
      }else{


        return this.purseService.deleteById(id).flatMap(c->{
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


  @GetMapping("/search/{numberIdentity}")
  public Mono<PurseDto> getByIdPurse(@PathVariable String numberIdentity){
    return this.purseService.findByNumberIdentity(numberIdentity);
  }
}

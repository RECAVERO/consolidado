package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.TypeService;
import com.nttdata.domain.models.ResponseDto;
import com.nttdata.domain.models.TypeDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/type")
public class TypeController {

  private final TypeService typeService;

  public TypeController(TypeService typeService) {
    this.typeService = typeService;
  }

  @GetMapping
  public Flux<TypeDto> getListClient(){
    return this.typeService.getListType();
  }
  @PostMapping
  public Mono<TypeDto> saveClient(@RequestBody Mono<TypeDto> typeDto){
    return typeDto.flatMap(type->{
      type.setCreationDate(this.getDateNow());
      type.setUpdatedDate(this.getDateNow());
      type.setActive(1);
      return this.typeService.saveType(Mono.just(type));
    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updateClient(@RequestBody Mono<TypeDto> typeDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return typeDto.flatMap(type->{
      return this.typeService.getByIdType(id).flatMap(typeClient->{
        if(typeClient.getId()==null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Type Client not Exits");
          return Mono.just(responseDto);
        }else{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Type Client Updated!");
          typeClient.setTypeClient(type.getTypeClient());
          typeClient.setUpdatedDate(this.getDateNow());

          return this.typeService.updateType(Mono.just(typeClient), id).flatMap(t->{
            responseDto.setTypeClient(t);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<TypeDto> getClientById(@PathVariable String id){
    return this.typeService.getByIdType(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deleteClientById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.typeService.getByIdType(id).flatMap(cli->{
      if(cli.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Client not Exits");
        return Mono.just(responseDto);
      }else{


        return this.typeService.deleteById(id).flatMap(c->{
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

  @GetMapping("/search/{idType}")
  public Mono<TypeDto> getByIdClient(@PathVariable String idType){
    return this.typeService.findByIdType(idType);
  }
}

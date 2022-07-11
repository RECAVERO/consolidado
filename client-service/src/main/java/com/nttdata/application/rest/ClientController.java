package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.ClientService;
import com.nttdata.domain.models.ClientDto;
import com.nttdata.domain.models.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/client")
public class ClientController {
  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public Flux<ClientDto> getListClient(){
    return this.clientService.getListClient();
  }
  @PostMapping
  public Mono<ResponseDto> saveClient(@RequestBody Mono<ClientDto> clientDto){
    ResponseDto responseDto=new ResponseDto();
    return clientDto.flatMap(client->{
      return this.clientService.findByIdClient(client.getIdClient()).flatMap(c->{
        if(c.getIdClient() == null){
          client.setCreatedDate(this.getDateNow());
          client.setUpdatedDate(this.getDateNow());
          client.setActive(1);
          return this.clientService.saveClient(Mono.just(client)).flatMap(x->{
            responseDto.setStatus(HttpStatus.CREATED.toString());
            responseDto.setMessage("Client Created");
            responseDto.setClient(x);
            return Mono.just(responseDto);
          });
        }else{
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Not Client Created");
          return Mono.just(responseDto);
        }
      });

    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updateClient(@RequestBody Mono<ClientDto> clientDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return clientDto.flatMap(client->{
      return this.clientService.getByIdClient(id).flatMap(cli->{
        if(cli.getId()==null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Client not Exits");
          return Mono.just(responseDto);
        }else{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Client Updated!");
          cli.setNames(client.getNames());
          cli.setEmail(client.getEmail());
          cli.setUpdatedDate(this.getDateNow());

          return this.clientService.updateClient(Mono.just(cli), id).flatMap(c->{
            responseDto.setClient(c);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<ClientDto> getClientById(@PathVariable String id){
    return this.clientService.getByIdClient(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deleteClientById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.clientService.getByIdClient(id).flatMap(cli->{
      if(cli.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Client not Exits");
        return Mono.just(responseDto);
      }else{


        return this.clientService.deleteById(id).flatMap(c->{
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


  @GetMapping("/search/{idClient}")
  public Mono<ClientDto> getByIdClient(@PathVariable String idClient){
    return this.clientService.findByIdClient(idClient);
  }

}

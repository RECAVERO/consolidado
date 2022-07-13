package com.nttdada.application.rest;

import com.nttdada.btask.interfaces.ProductService;
import com.nttdada.domain.models.ProductDto;
import com.nttdada.domain.models.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/product")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }
  @GetMapping
  public Flux<ProductDto> getListPurse(){
    return this.productService.getListProduct();
  }
  @PostMapping
  public Mono<ResponseDto> savePurse(@RequestBody Mono<ProductDto> purseDto){
    ResponseDto responseDto=new ResponseDto();
    return purseDto.flatMap(purse->{
      purse.setUpdatedDate(this.getDateNow());
      purse.setCreationDate(this.getDateNow());
      purse.setActive(1);
      return this.productService.saveProduct(Mono.just(purse)).flatMap(x->{
        responseDto.setStatus(HttpStatus.CREATED.toString());
        responseDto.setMessage("Purse Created");
        responseDto.setProduct(x);
        return Mono.just(responseDto);
      });
    });
  }


  @PutMapping("/{id}")
  public Mono<ResponseDto> updatePurse(@RequestBody Mono<ProductDto> currencyDto, @PathVariable String id){
    ResponseDto responseDto=new ResponseDto();
    return currencyDto.flatMap(currency->{
      return this.productService.getByIdProduct(id).flatMap(purse->{
        if(purse.getId()==null){
          responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
          responseDto.setMessage("Purse not Exits");
          return Mono.just(responseDto);
        }else{
          responseDto.setStatus(HttpStatus.OK.toString());
          responseDto.setMessage("Purse Updated!");
          purse.setNumberCell(currency.getNumberCell());
          purse.setUpdatedDate(this.getDateNow());
          return this.productService.updateProduct(Mono.just(purse), id).flatMap(c->{
            responseDto.setProduct(c);
            return Mono.just(responseDto);
          });
        }
      });
    });
  }

  @GetMapping("/{id}")
  public Mono<ProductDto> getPurseById(@PathVariable String id){
    return this.productService.getByIdProduct(id);
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseDto> deletePurseById(@PathVariable String id){
    ResponseDto responseDto=new ResponseDto();

    return this.productService.getByIdProduct(id).flatMap(cli->{
      if(cli.getId()==null){
        responseDto.setStatus(HttpStatus.NOT_FOUND.toString());
        responseDto.setMessage("Purse not Exits");
        return Mono.just(responseDto);
      }else{


        return this.productService.deleteById(id).flatMap(c->{
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

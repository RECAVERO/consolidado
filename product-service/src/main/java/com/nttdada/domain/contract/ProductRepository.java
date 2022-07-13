package com.nttdada.domain.contract;

import com.nttdada.domain.models.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
  Flux<ProductDto> getListProduct();
  Mono<ProductDto> saveProduct(Mono<ProductDto> productDto);
  Mono<ProductDto> updateProduct(Mono<ProductDto> productDto, String id);
  Mono<ProductDto> getByIdProduct(String id);
  Mono<Void> deleteById(String id);
}

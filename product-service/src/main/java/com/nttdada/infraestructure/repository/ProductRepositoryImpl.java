package com.nttdada.infraestructure.repository;

import com.nttdada.domain.contract.ProductRepository;
import com.nttdada.domain.models.ProductDto;
import com.nttdada.infraestructure.mongodb.ProductRepositoryMongodb;
import com.nttdada.utils.convert.Convert;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
  private final ProductRepositoryMongodb productRepositoryMongodb;

  public ProductRepositoryImpl(ProductRepositoryMongodb productRepositoryMongodb) {
    this.productRepositoryMongodb = productRepositoryMongodb;
  }

  @Override
  public Flux<ProductDto> getListProduct() {
    return this.productRepositoryMongodb.findAll().map(Convert::entityToDto);
  }

  @Override
  public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto) {
    return productDto.map(Convert::dtoToEntity)
        .flatMap(this.productRepositoryMongodb::insert)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<ProductDto> updateProduct(Mono<ProductDto> productDto, String id) {
    return  this.productRepositoryMongodb.findById(id)
        .flatMap(p -> productDto.map(Convert::dtoToEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.productRepositoryMongodb::save)
        .map(Convert::entityToDto);
  }

  @Override
  public Mono<ProductDto> getByIdProduct(String id) {
    return this.productRepositoryMongodb.findById(id)
        .map(Convert::entityToDto).defaultIfEmpty(new ProductDto());
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.productRepositoryMongodb.deleteById(id);
  }
}

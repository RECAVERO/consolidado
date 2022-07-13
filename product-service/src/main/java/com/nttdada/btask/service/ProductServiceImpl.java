package com.nttdada.btask.service;

import com.nttdada.btask.interfaces.ProductService;
import com.nttdada.domain.contract.ProductRepository;
import com.nttdada.domain.models.ProductDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public ProductServiceImpl(ProductRepository productRepository, KafkaTemplate<String, String> kafkaTemplate) {
    this.productRepository = productRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public Flux<ProductDto> getListProduct() {
    return this.productRepository.getListProduct();
  }

  @Override
  public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto) {
    return this.productRepository.saveProduct(productDto).flatMap(purchase->{
      System.out.println("Received " + UUID.randomUUID());
      this.kafkaTemplate.send("topic-3",UUID.randomUUID().toString());
      return Mono.just(purchase);
    });
  }

  @Override
  public Mono<ProductDto> updateProduct(Mono<ProductDto> productDto, String id) {
    return this.productRepository.updateProduct(productDto, id);
  }

  @Override
  public Mono<ProductDto> getByIdProduct(String id) {
    return this.productRepository.getByIdProduct(id);
  }

  @Override
  public Mono<Void> deleteById(String id) {
    return this.productRepository.deleteById(id);
  }
}

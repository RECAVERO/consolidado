package com.nttdada.infraestructure.mongodb;

import com.nttdada.infraestructure.document.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepositoryMongodb extends ReactiveMongoRepository<Product, String> {
}

package com.nttdada.utils.convert;

import com.nttdada.domain.models.ProductDto;
import com.nttdada.infraestructure.document.Product;
import org.springframework.beans.BeanUtils;

public class Convert {
  public static ProductDto entityToDto(Product product) {
    ProductDto productDto = new ProductDto();
    BeanUtils.copyProperties(product, productDto);
    return productDto;
  }

  public static Product dtoToEntity(ProductDto productDto) {
    Product product = new Product();
    BeanUtils.copyProperties(productDto, product);
    return product;
  }
}

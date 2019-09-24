package br.com.monolithic.ecommerce.repository;

import br.com.monolithic.ecommerce.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}

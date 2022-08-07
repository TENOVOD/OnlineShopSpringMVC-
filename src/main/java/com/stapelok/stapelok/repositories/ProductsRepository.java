package com.stapelok.stapelok.repositories;

import com.stapelok.stapelok.models.Products;
import org.springframework.data.repository.CrudRepository;



public interface ProductsRepository extends CrudRepository<Products,Long> {

}

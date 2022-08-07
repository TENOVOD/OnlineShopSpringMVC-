package com.stapelok.stapelok.repositories;

import com.stapelok.stapelok.models.Products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface ProductsRepository extends CrudRepository<Products,Long> {


}

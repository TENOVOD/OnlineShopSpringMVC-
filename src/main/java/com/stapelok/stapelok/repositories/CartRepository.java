package com.stapelok.stapelok.repositories;


import com.stapelok.stapelok.models.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart,Long> {

    @Query(value="SELECT MAX(id)  FROM cart", nativeQuery = true)
    int maxval();

}

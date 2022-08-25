package com.stapelok.stapelok.repositories;


import com.stapelok.stapelok.models.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CartRepository extends CrudRepository<Cart,Long> {

    @Query(value="SELECT MAX(id) FROM cart", nativeQuery = true)
    String maxval();
    @Query(value = "SELECT COUNT(user_id) FROM cart WHERE user_id=:userid", nativeQuery = true)
    String countProd(@Param("userid") String userid);

    @Query(value = "SELECT * FROM cart WHERE user_id=:userid", nativeQuery = true)
    ArrayList<Cart> arrCart(@Param("userid") String userid);

}

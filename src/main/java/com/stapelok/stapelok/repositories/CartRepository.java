package com.stapelok.stapelok.repositories;


import com.stapelok.stapelok.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query(value="SELECT MAX(id) FROM cart", nativeQuery = true)
    Long maxval();
    @Query(value = "SELECT COUNT(user_id) FROM cart WHERE user_id=:userid", nativeQuery = true)
    String countProd(@Param("userid") String userid);

    @Query(value = "SELECT * FROM cart WHERE user_id=:userid", nativeQuery = true)
    ArrayList<Cart> arrCart(@Param("userid") String userid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart WHERE user_id=:usid",nativeQuery = true)
    void deleteAfterOrder(@Param("usid") String usid);

}

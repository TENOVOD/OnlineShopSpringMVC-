package com.stapelok.stapelok.repositories;

import com.stapelok.stapelok.models.Cart;
import com.stapelok.stapelok.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value="SELECT * FROM orders WHERE user_ac_id=:userEmail", nativeQuery = true)
    ArrayList<Order> getOrdersByUserEmail(@Param("userEmail") String userEmail);


}

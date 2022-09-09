package com.stapelok.stapelok.repositories;

import com.stapelok.stapelok.models.PreOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface PreOrderRepository extends CrudRepository<PreOrder,Long> {

    @Query(value="SELECT * FROM pre_order WHERE user_email=:userEmail", nativeQuery = true)
    ArrayList<PreOrder> getPreOrdersByUserEmail(@Param("userEmail") String userEmail);

    @Query(value = "SELECT * FROM pre_order WHERE order_id=:order_id",nativeQuery = true)
    ArrayList<PreOrder> getPreOrderByOrder_id(@Param("order_id") long order_id);

}

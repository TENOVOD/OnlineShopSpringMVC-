package com.stapelok.stapelok.repositories;

import com.stapelok.stapelok.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {

    @PreAuthorize("hasAuthority('developers:read')")
    @Query(value = "SELECT * FROM products WHERE id=:prodid", nativeQuery = true)
    ArrayList<Products> getById(@Param("prodid") long prodid);

}

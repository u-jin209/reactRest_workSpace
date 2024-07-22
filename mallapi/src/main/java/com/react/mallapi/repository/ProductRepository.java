package com.react.mallapi.repository;

import com.react.mallapi.domain.Product;
import com.react.mallapi.repository.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> , ProductSearch {

    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") long pno);

    @Modifying
    @Query("update Product p set p.delFlag =:delFlag where p.pno =:pno")
    void updateToDelete(@Param("pno") long pno,@Param("delFlag") boolean delFlag );

    @Query("select p, pi from Product p left join p.imageList pi where pi.ord =0 and p.delFlag =false")
    Page<Object[]> selectList(Pageable pageable);
}


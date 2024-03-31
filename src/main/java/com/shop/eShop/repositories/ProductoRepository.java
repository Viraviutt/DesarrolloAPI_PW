package com.shop.eShop.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.Producto;

@RepositoryRestController
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /* find by nombre */
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Producto> findByNombre(String nombre);

}

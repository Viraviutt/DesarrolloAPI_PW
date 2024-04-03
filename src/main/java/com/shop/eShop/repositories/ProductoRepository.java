package com.shop.eShop.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.Producto;

@RepositoryRestController
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /* find by nombre */
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Optional<List<Producto>> findByNombre(String nombre);

    /* find by stock */
    @Query("SELECT p FROM Producto p WHERE p.stock > 0")
    Optional<List<Producto>> findByStock();

    /* find by precio y stock */
    @Query("SELECT p FROM Producto p WHERE p.precio < ?1 AND p.stock = ?2")
    Optional<List<Producto>> findByPrecioANDStock(Double precio, Integer stock);
    

}

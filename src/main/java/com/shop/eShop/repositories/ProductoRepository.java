package com.shop.eShop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.Producto;

@RepositoryRestController
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    /* find by id */
    Optional<Producto> findById(Long id);
}

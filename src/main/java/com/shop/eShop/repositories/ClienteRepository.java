package com.shop.eShop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shop.eShop.entities.Cliente;

@RepositoryRestResource
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /* find by nombre que inicie por */
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT(?1, '%'))")
    Optional<List<Cliente>> findByNombre(String nombre);

    /* find by correo */
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.correo) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Optional<List<Cliente>> findByCorreo(String correo);

    /** find by direccion */
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.direccion) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Optional<List<Cliente>> findByDireccion(String direccion);
    
}

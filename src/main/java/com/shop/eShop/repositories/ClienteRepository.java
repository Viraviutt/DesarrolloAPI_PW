package com.shop.eShop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shop.eShop.entities.Cliente;

@RepositoryRestResource
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /* find by email */
    @Query("SELECT c FROM Cliente c WHERE c.email = ?1")
    Cliente findByEmail(String correo);

    /** find by address */
    @Query("SELECT c FROM Cliente c WHERE c.address = ?1")
    List<Cliente> findByDireccion(String direccion);

}

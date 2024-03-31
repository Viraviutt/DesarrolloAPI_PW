package com.shop.eShop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.DetalleEnvio;
import com.shop.eShop.enums.Estado;


@RepositoryRestController
public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio, Long> {

    /* find by id pedido */
    @Query("SELECT d FROM DetalleEnvio d WHERE d.idPedido.idPedido = ?1")
    DetalleEnvio findByIdDelPedido(Long id);

    /* find by transportadora */
    @Query("SELECT d FROM DetalleEnvio d WHERE LOWER(d.transportadora) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<DetalleEnvio> findByTransportadora(String transportadora);

    /* find by numero de rastreo */
    @Query("SELECT d FROM DetalleEnvio d WHERE d.numeroDeRastreo = ?1")
    DetalleEnvio findByNumeroDeRastreo(String numeroRastreo);

    /* find by estado del pedido */
    @Query("SELECT d FROM DetalleEnvio d WHERE d.idPedido.estado = ?1")
    DetalleEnvio findByEstadoDelPedid(Estado estado);
}

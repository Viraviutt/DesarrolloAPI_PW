package com.shop.eShop.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.Pago;
import com.shop.eShop.enums.metodoPago;

@RepositoryRestController
public interface PagoRepository extends JpaRepository<Pago, Long> {

    /* find by pagos de una fecha */
    @Query("SELECT p FROM Pago p WHERE p.fechaPago = ?1")
    List<Pago> findByFecha(Timestamp fecha);

    /* find by pagos antes de una fecha */
    @Query("SELECT p FROM Pago p WHERE p.fechaPago < ?1")
    List<Pago> findByFechaBefore(Timestamp fecha);

    /* find by pagos despues de una fecha */
    @Query("SELECT p FROM Pago p WHERE p.fechaPago > ?1")
    List<Pago> findByFechaAfter(Timestamp fecha);

    /* find by metodo de pago */
    @Query("SELECT p FROM Pago p WHERE p.metodoPago = ?1")
    List<Pago> findByMetodoPago(metodoPago metodoPago);

}

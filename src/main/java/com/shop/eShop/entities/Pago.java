package com.shop.eShop.entities;

import java.sql.Timestamp;

import com.shop.eShop.enums.metodoPago;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @ManyToOne
    @JoinColumn(referencedColumnName = "idPedido")
    private Pedido idPedido;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaPago;

    @Column(nullable = false)
    private metodoPago metodoPago;
}

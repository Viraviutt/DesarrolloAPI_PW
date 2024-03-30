package com.shop.eShop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name = "itemsdelpedido")
@NoArgsConstructor
@AllArgsConstructor
public class ItemDelPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemsDelPedido;

    @ManyToOne
    @JoinColumn(referencedColumnName = "idPedido")
    private Pedido idPedido;

    @ManyToOne
    @JoinColumn(referencedColumnName = "idProducto")
    private Producto idProducto;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false)
    private Double precioUnitario;
}

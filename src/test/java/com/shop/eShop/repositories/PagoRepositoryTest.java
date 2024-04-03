package com.shop.eShop.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.yaml.snakeyaml.scanner.Scanner;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.shop.eShop.AbstractIntegrationDBTest;
import com.shop.eShop.entities.Cliente;
import com.shop.eShop.entities.Pago;
import com.shop.eShop.entities.Pedido;

public class PagoRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PagoRepository pagoRepository;

    Cliente cliente1 = Cliente.builder()
                    .nombre("Carlos")
                    .correo("Carlos@example.com")
                    .direccion("Calle 1 #2-3")
                    .build();
                    
    Cliente cliente2 = Cliente.builder()
                    .nombre("Carmen")
                    .correo("carmen@example.com")
                    .direccion("Calle 1 #5-6")
                    .build();

    Pedido pedido1 = Pedido.builder()
                    .idCliente(cliente1)
                    .fechaPedido(Timestamp.valueOf(LocalDateTime.of(2024, 3, 28, 12, 35, 32)))
                    .estado("PENDIENTE")
                    .build();

    Pedido pedido2 = Pedido.builder()
                    .idCliente(cliente2)
                    .fechaPedido(Timestamp.valueOf(LocalDateTime.of(2023, 5, 27, 15, 24, 14)))
                    .estado("ENTREGADO")
                    .build();

    Pedido pedido3 = Pedido.builder()
                    .idCliente(cliente1)
                    .fechaPedido(Timestamp.valueOf(LocalDateTime.of(2024, 2, 15, 12, 35, 32)))
                    .estado("ENVIADO")
                    .build();

    Pedido pedido4 = Pedido.builder()
                    .idCliente(cliente2)
                    .fechaPedido(Timestamp.valueOf(LocalDateTime.of(2024, 3, 25, 18, 20, 46)))
                    .estado("PENDIENTE")
                    .build();

    Pago pago1 = Pago.builder()
                .idPedido(pedido3)
                .monto(1234.56)
                .fechaPago(Timestamp.valueOf(LocalDateTime.of(2024, 2, 13, 10, 38, 12)))
                .metodoPago("PSE")
                .build();

    Pago pago2 = Pago.builder()
                .idPedido(pedido1)
                .monto(3342.58)
                .fechaPago(Timestamp.valueOf(LocalDateTime.of(2024, 3, 24, 12, 35, 32)))
                .metodoPago("NEQUI")
                .build();

    Pago pago3 = Pago.builder()
                .idPedido(pedido4)
                .monto(632.23)
                .fechaPago(Timestamp.valueOf(LocalDateTime.of(2024, 3, 25, 18, 20, 46)))
                .metodoPago("EFECTIVO")
                .build();

    Pago pago4 = Pago.builder()
                .idPedido(pedido2)
                .monto(580D)
                .fechaPago(Timestamp.valueOf(LocalDateTime.of(2024, 1, 10, 10, 38, 12)))
                .metodoPago("TARJETA_CREDITO")
                .build();

    Pago pago5 = Pago.builder()
                .idPedido(pedido2)
                .monto(596D)
                .fechaPago(Timestamp.valueOf(LocalDateTime.of(2024, 2, 10, 10, 38, 12)))
                .metodoPago("TARJETA_CREDITO")
                .build();

    @BeforeEach
    void setUp() {
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
    }

    @AfterEach
    void tearDown() {
        pagoRepository.deleteAll();
        pagoRepository.flush();
        pedidoRepository.deleteAll();
        pedidoRepository.flush();
        clienteRepository.deleteAll();
        clienteRepository.flush();
    }

    @Test
    void testCreate(){ //CREATE
        //given pago
        //when
        Pago pagoSaved = pagoRepository.save(pago1);
        Pago pagoSaved2 = pagoRepository.save(pago4);
        //then
        assertNotNull(pagoSaved);
        assertNotNull(pagoSaved2);

    }

    @Test
    void testFindAll(){ //READ
        //given pedido
        //when
        pagoRepository.save(pago1);
        pagoRepository.save(pago2);
        pagoRepository.save(pago3);
        pagoRepository.save(pago4);
        List<Pago> pagoEncontrado = pagoRepository.findAll();
        //then
        assertNotNull(pagoEncontrado);
        assertEquals(4, pagoEncontrado.size());

    }

    @Test
    void testFindById(){ //READ
        //given pedido
        //when
        Long id1 = pagoRepository.save(pago2).getIdPago();
        Long id2 = pagoRepository.save(pago3).getIdPago();
        Pago pagoEncontrado1 = pagoRepository.findById(id1).orElse(null);
        Pago pagoEncontrado2 = pagoRepository.findById(id2).orElse(null);
        
        //then
        assertEquals("Carlos", pagoEncontrado1.getIdPedido().getIdCliente().getNombre());
        assertEquals("NEQUI", pagoEncontrado1.getMetodoPago());
        assertEquals(3342.58, pagoEncontrado1.getMonto());
        assertEquals("Carmen", pagoEncontrado2.getIdPedido().getIdCliente().getNombre());
        assertEquals(632.23, pagoEncontrado2.getMonto());
        assertEquals("EFECTIVO", pagoEncontrado2.getMetodoPago());

    }

    @Test
    void testUpdate(){ //UPDATE
        //given pedido
        //when
        pagoRepository.save(pago2);
        pago2.setMetodoPago("EFECTIVO");
        pagoRepository.save(pago2);

        Pago pagoEncontrado1 = pagoRepository.findById(pago2.getIdPago()).orElse(null);
        //then
        assertNotNull(pagoEncontrado1);
        assertEquals("EFECTIVO", pagoEncontrado1.getMetodoPago());

    }

    @Test
    void testDelete(){ //DELETE
        //given pedido
        //when
        Long id = pagoRepository.save(pago1).getIdPago();
        pagoRepository.deleteById(id);
        Pago pagoEncontrado = pagoRepository.findById(id).orElse(null);
        //then
        assertNull(pagoEncontrado);

    }

    @Test
    void testFindByFecha() {

    }

    @Test
    void testFindByFechaAfter() {

    }

    @Test
    void testFindByFechaBefore() {

    }

    @Test
    void testFindByFechaBetween() {

    }

    @Test
    void testFindByIdPedidoAndMetodo() {

    }

    @Test
    void testFindByMetodoPago() {

    }
}

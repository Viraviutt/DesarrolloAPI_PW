package com.shop.eShop.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        pagoRepository.deleteAll();
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
        pedidoRepository.deleteAll();
        
    }

    @Test
    void givenAnPago_whenSave_thenClientewithId(){ //CREATE
        //given pago
        //when
        Pago pagoSaved = pagoRepository.save(pago1);
        Pago pagoSaved2 = pagoRepository.save(pago4);
        //then
        assertNotNull(pagoSaved);
        assertNotNull(pagoSaved2);

    }

    @Test
    void givenAnPedido_testFindAll(){ //READ
        //given pedido
        //when
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        List<Pedido> pedidoEncontrado = pedidoRepository.findAll();
        //then
        assertNotNull(pedidoEncontrado);
        assertEquals(4, pedidoEncontrado.size());

    }

    @Test
    void givenAnPedido_testFindById(){ //READ
        //given pedido
        //when
        Long id1 = pedidoRepository.save(pedido2).getIdPedido();
        Long id2 = pedidoRepository.save(pedido3).getIdPedido();
        Pedido pedidoEncontrado1 = pedidoRepository.findById(id1).orElse(null);
        Pedido pedidoEncontrado2 = pedidoRepository.findById(id2).orElse(null);
        
        //then
        assertEquals("Carmen", pedidoEncontrado1.getIdCliente().getNombre());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2023, 5, 27, 15, 24, 14)), pedidoEncontrado1.getFechaPedido());
        assertEquals("ENTREGADO", pedidoEncontrado1.getEstado());
        assertEquals("Carlos", pedidoEncontrado2.getIdCliente().getNombre());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 2, 15, 12, 35, 32)), pedidoEncontrado2.getFechaPedido());
        assertEquals("ENVIADO", pedidoEncontrado2.getEstado());

    }

    @Test
    void givenAnCliente_testUpdate(){ //UPDATE
        //given pedido
        //when
        pedidoRepository.save(pedido2);
        pedido2.setEstado("ENVIADO");
        pedidoRepository.save(pedido2);

        pedidoRepository.save(pedido1);
        pedido1.setFechaPedido(Timestamp.valueOf(LocalDateTime.of(2024, 3, 26, 18, 20, 46)));
        pedidoRepository.save(pedido1);

        Pedido pedidoEncontrado1 = pedidoRepository.findById(pedido2.getIdPedido()).orElse(null);
        Pedido pedidoEncontrado2 = pedidoRepository.findById(pedido1.getIdPedido()).orElse(null);
        //then
        assertNotNull(pedidoEncontrado1);
        assertNotNull(pedidoEncontrado2);
        assertEquals("ENVIADO", pedidoEncontrado1.getEstado());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 3, 26, 18, 20, 46)), pedidoEncontrado2.getFechaPedido());

    }

    @Test
    void givenAnCliente_testDelete(){ //DELETE
        //given pedido
        //when
        Long id = pedidoRepository.save(pedido1).getIdPedido();
        pedidoRepository.deleteById(id);
        Pedido pedidoEncontrado = pedidoRepository.findById(id).orElse(null);
        //then
        assertNull(pedidoEncontrado);

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

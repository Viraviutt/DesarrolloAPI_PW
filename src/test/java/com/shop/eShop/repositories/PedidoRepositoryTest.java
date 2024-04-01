package com.shop.eShop.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shop.eShop.AbstractIntegrationDBTest;
import com.shop.eShop.entities.Cliente;
import com.shop.eShop.entities.Pedido;

public class PedidoRepositoryTest extends AbstractIntegrationDBTest{

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

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


    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
    }

    @Test
    void givenAnPedido_whenSave_thenClientewithId(){ //CREATE
        //given pedido
        //when
        Pedido pedidoSaved = pedidoRepository.save(pedido1);
        Pedido pedidoSaved2 = pedidoRepository.save(pedido3);
        //then
        assertNotNull(pedidoSaved);
        assertNotNull(pedidoSaved2);

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
    void testFindByCliente() {
        //given cliente
        
        //when
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        List<Pedido> pedidoEncontrado = pedidoRepository.findByCliente(cliente1);
        //then
        assertNotNull(pedidoEncontrado);
        assertEquals(2, pedidoEncontrado.size());
        assertEquals(cliente1, pedidoEncontrado.get(0).getIdCliente());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 3, 28, 12, 35, 32)), pedidoEncontrado.get(0).getFechaPedido());
        assertEquals("PENDIENTE", pedidoEncontrado.get(0).getEstado());
        assertEquals(cliente1, pedidoEncontrado.get(1).getIdCliente());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 2, 15, 12, 35, 32)), pedidoEncontrado.get(1).getFechaPedido());
        assertEquals("ENVIADO", pedidoEncontrado.get(1).getEstado());
    }

    @Test
    void testFindByClienteAndEstado() {
        //given cliente y estado
        
        //when
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        List<Pedido> pedidoEncontrado = pedidoRepository.findByClienteAndEstado(cliente1, "PENDIENTE");
        //then
        assertNotNull(pedidoEncontrado);
        assertEquals(1, pedidoEncontrado.size());
        assertEquals(cliente1, pedidoEncontrado.get(0).getIdCliente());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 3, 28, 12, 35, 32)), pedidoEncontrado.get(0).getFechaPedido());
        assertEquals("PENDIENTE", pedidoEncontrado.get(0).getEstado());
    }

    @Test
    void testFindByEstado() {
        //given estado
        
        //when
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        List<Pedido> pedidoEncontrado = pedidoRepository.findByEstado("PENDIENTE");
        //then
        assertNotNull(pedidoEncontrado);
        assertEquals(2, pedidoEncontrado.size());
        assertEquals(cliente1, pedidoEncontrado.get(0).getIdCliente());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 3, 28, 12, 35, 32)), pedidoEncontrado.get(0).getFechaPedido());
        assertEquals(cliente2, pedidoEncontrado.get(1).getIdCliente());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 3, 25, 18, 20, 46)), pedidoEncontrado.get(1).getFechaPedido());
    }

    @Test
    void testFindByFecha() {
        //given fecha
        
        //when
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        List<Pedido> pedidoEncontrado = pedidoRepository.findByFecha(Timestamp.valueOf(LocalDateTime.of(2024, 3, 28, 12, 35, 32)));
        //then
        assertNotNull(pedidoEncontrado);
        assertEquals(1, pedidoEncontrado.size());
        assertEquals("Carlos", pedidoEncontrado.get(0).getIdCliente().getNombre());
        assertEquals(cliente1, pedidoEncontrado.get(0).getIdCliente());
        assertEquals(Timestamp.valueOf(LocalDateTime.of(2024, 3, 28, 12, 35, 32)), pedidoEncontrado.get(0).getFechaPedido());
    }

    @Test
    void testFindByFechaAfter() {
        //given fecha
        
        //when
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        List<Pedido> pedidoEncontrado = pedidoRepository.findByFechaAfter(Timestamp.valueOf(LocalDateTime.of(2023, 5, 27, 15, 24, 14)));
        //then
        assertNotNull(pedidoEncontrado);
        assertEquals(3, pedidoEncontrado.size());
        assertEquals(cliente1, pedidoEncontrado.get(0).getIdCliente());
        assertEquals(cliente1, pedidoEncontrado.get(1).getIdCliente());
        assertEquals(cliente2, pedidoEncontrado.get(2).getIdCliente());
    }

    @Test
    void testFindByFechaBefore() {
        //given fecha
        
        //when
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        List<Pedido> pedidoEncontrado = pedidoRepository.findByFechaBefore(Timestamp.valueOf(LocalDateTime.now()));
        //then
        assertNotNull(pedidoEncontrado);
        assertEquals(4, pedidoEncontrado.size());
        assertEquals(cliente1, pedidoEncontrado.get(0).getIdCliente());
        assertEquals(cliente2, pedidoEncontrado.get(1).getIdCliente());
        assertEquals(cliente1, pedidoEncontrado.get(2).getIdCliente());
        assertEquals(cliente2, pedidoEncontrado.get(3).getIdCliente());
    }

    @Test
    void testFindByFechaBetween() {
        //given fecha
        
        //when
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);
        pedidoRepository.save(pedido4);
        List<Pedido> pedidoEncontrado = pedidoRepository.findByFechaBetween(Timestamp.valueOf(LocalDateTime.of(2022, 5, 27, 15, 24, 14)), Timestamp.valueOf(LocalDateTime.of(2024, 3, 26, 18, 20, 46)));
        //then
        assertNotNull(pedidoEncontrado);
        assertEquals(3, pedidoEncontrado.size());
        assertEquals(cliente2, pedidoEncontrado.get(0).getIdCliente());
        assertEquals("ENTREGADO", pedidoEncontrado.get(0).getEstado());
        assertEquals(cliente1, pedidoEncontrado.get(1).getIdCliente());
        assertEquals("ENVIADO", pedidoEncontrado.get(1).getEstado());
        assertEquals(cliente2, pedidoEncontrado.get(2).getIdCliente());
        assertEquals("PENDIENTE", pedidoEncontrado.get(2).getEstado());
    }
}

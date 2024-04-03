package com.shop.eShop.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shop.eShop.AbstractIntegrationDBTest;
import com.shop.eShop.entities.Cliente;

@SuppressWarnings("null")
public class ClienteRepositoryTest extends AbstractIntegrationDBTest {

    @Autowired
    private ClienteRepository clienteRepository;

    Cliente cliente = Cliente.builder()
                    .nombre("Julian")
                    .correo("julian@example.com")
                    .direccion("Calle 1 #2-3")
                    .build();

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

    

    @BeforeEach
    void setUp(){
        clienteRepository.deleteAll();
    }

    @Test
    void givenAnCliente_whenSave_thenClientewithId(){ //CREATE
        //given cliente
        //when
        Cliente clienteSaved = clienteRepository.save(cliente1);
        Cliente clienteSaved2 = clienteRepository.save(cliente2);
        //then
        assertNotNull(clienteSaved);
        assertNotNull(clienteSaved2);

    }

    @Test
    void givenAnCliente_testFindAll(){ //READ
        //given cliente
        //when
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        List<Cliente> clienteEncontrado = clienteRepository.findAll();
        //then
        assertNotNull(clienteEncontrado);
        assertEquals(2, clienteEncontrado.size());

    }

    @Test
    void givenAnCliente_testFindById(){ //READ
        //given cliente
        //when
        Long id1 = clienteRepository.save(cliente1).getIdCliente();
        Long id2 = clienteRepository.save(cliente2).getIdCliente();
        Cliente clienteEncontrado1 = clienteRepository.findById(id1).orElse(null);
        Cliente clienteEncontrado2 = clienteRepository.findById(id2).orElse(null);
        
        //then
        assertEquals("Carlos", clienteEncontrado1.getNombre());
        assertEquals("Carlos@example.com", clienteEncontrado1.getCorreo());
        assertEquals("Calle 1 #2-3", clienteEncontrado1.getDireccion());
        assertEquals("Carmen", clienteEncontrado2.getNombre());
        assertEquals("carmen@example.com", clienteEncontrado2.getCorreo());
        assertEquals("Calle 1 #5-6", clienteEncontrado2.getDireccion());

    }

    @Test
    void givenAnCliente_testUpdate(){ //UPDATE
        //given cliente
        //when
        clienteRepository.save(cliente1);
        cliente1.setNombre("Pepe");
        clienteRepository.save(cliente1);

        clienteRepository.save(cliente2);
        cliente2.setCorreo("Carmencita@example.com");
        clienteRepository.save(cliente2);

        Cliente clienteEncontrado1 = clienteRepository.findById(cliente1.getIdCliente()).orElse(null);
        Cliente clienteEncontrado2 = clienteRepository.findById(cliente2.getIdCliente()).orElse(null);
        //then
        assertNotNull(clienteEncontrado1);
        assertNotNull(clienteEncontrado2);
        assertEquals("Pepe", clienteEncontrado1.getNombre());
        assertEquals("Carmencita@example.com", clienteEncontrado2.getCorreo());

    }

    @Test
    void givenAnCliente_testDelete(){ //DELETE
        //given cliente
        //when
        Long id = clienteRepository.save(cliente1).getIdCliente();
        clienteRepository.deleteById(id);
        Cliente clienteEncontrado = clienteRepository.findById(id).orElse(null);
        //then
        assertNull(clienteEncontrado);

    }

    @Test
    void givenAnCliente_whenFindByCorreo_thenReturnClient(){
        //given cliente
        
        //when
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        List<Cliente> clienteEncontrado = clienteRepository.findByCorreo("car").orElse(null);
        //then
        assertNotNull(clienteEncontrado);
        assertEquals(2, clienteEncontrado.size());
        assertEquals("Carlos", clienteEncontrado.get(0).getNombre());
        assertEquals("Carmen", clienteEncontrado.get(1).getNombre());
        assertEquals("Calle 1 #2-3", clienteEncontrado.get(0).getDireccion());
        assertEquals("Calle 1 #5-6", clienteEncontrado.get(1).getDireccion());
    }

    @Test
    void givenAnCliente_whenFindByDireccion_thenReturnClient(){
        //given cliente
        
        //when
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        List<Cliente> clienteEncontrado = clienteRepository.findByDireccion("CaLle 1").orElse(null);
        //then
        assertNotNull(clienteEncontrado);
        assertEquals(2, clienteEncontrado.size());
        assertEquals("Carlos", clienteEncontrado.get(0).getNombre());
        assertEquals("Carmen", clienteEncontrado.get(1).getNombre());
    }

    @Test
    void givenAnCliente_whenFindByNombre_thenReturnClient(){
        //given cliente
        
        //when
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        List<Cliente> clienteEncontrado = clienteRepository.findByNombre("CAr").orElse(null);
        //then
        assertNotNull(clienteEncontrado);
        assertEquals(2, clienteEncontrado.size());
        assertEquals("Carlos", clienteEncontrado.get(0).getNombre());
        assertEquals("Carmen", clienteEncontrado.get(1).getNombre());
    }
}

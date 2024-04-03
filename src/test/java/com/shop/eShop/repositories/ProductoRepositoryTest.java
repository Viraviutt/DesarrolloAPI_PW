package com.shop.eShop.repositories;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shop.eShop.AbstractIntegrationDBTest;
import com.shop.eShop.entities.Producto;

@SuppressWarnings("null")
public class ProductoRepositoryTest extends AbstractIntegrationDBTest{

    @Autowired
    private ProductoRepository productoRepository;

    Producto producto1 = Producto.builder()
                    .nombre("Leche")
                    .precio(1234.56)
                    .stock(7)
                    .build();
                    
    Producto producto2 = Producto.builder()
                    .nombre("Manzana")
                    .precio(789.12)
                    .stock(4)
                    .build();
    
    Producto producto3 = Producto.builder()
                    .nombre("Lechuga")
                    .precio(12.23)
                    .stock(4)
                    .build();

    @BeforeEach
    void setUp(){
        productoRepository.deleteAll();
    }
    
    @Test
    void givenAnProducto_whenSave_thenProductowithId(){ //CREATE
        //given Producto
        //when
        Producto productoSaved = productoRepository.save(producto1);
        Producto productoSaved2 = productoRepository.save(producto2);
        //then
        assertNotNull(productoSaved);
        assertNotNull(productoSaved2);

    }

    @Test
    void givenAnProducto_testFindAll(){ //READ
        //given Producto
        //when
        productoRepository.save(producto1);
        productoRepository.save(producto2);
        List<Producto> productoEncontrado = productoRepository.findAll();
        //then
        assertNotNull(productoEncontrado);
        assertEquals(2, productoEncontrado.size());

    }

    @Test
    void givenAnProducto_testFindById(){ //READ
        //given Producto
        //when
        Long id1 = productoRepository.save(producto1).getIdProducto();
        Long id2 = productoRepository.save(producto2).getIdProducto();
        Producto productoEncontrado1 = productoRepository.findById(id1).orElse(null);
        Producto productoEncontrado2 = productoRepository.findById(id2).orElse(null);
        
        //then
        assertEquals("Leche", productoEncontrado1.getNombre());
        assertEquals(1234.56, productoEncontrado1.getPrecio());
        assertEquals(7, productoEncontrado1.getStock());
        assertEquals("Manzana", productoEncontrado2.getNombre());
        assertEquals(789.12, productoEncontrado2.getPrecio());
        assertEquals(4, productoEncontrado2.getStock());

    }

    @Test
    void givenAnProducto_testUpdate(){ //UPDATE
        //given Producto
        //when
        productoRepository.save(producto1);
        producto1.setNombre("Carne");
        productoRepository.save(producto1);

        productoRepository.save(producto2);
        producto2.setPrecio(3427.34);
        productoRepository.save(producto2);

        Producto productoEncontrado1 = productoRepository.findById(producto1.getIdProducto()).orElse(null);
        Producto productoEncontrado2 = productoRepository.findById(producto2.getIdProducto()).orElse(null);
        //then
        assertNotNull(productoEncontrado1);
        assertNotNull(productoEncontrado2);
        assertEquals("Carne", productoEncontrado1.getNombre());
        assertEquals(3427.34, productoEncontrado2.getPrecio());

    }

    @Test
    void givenAnProducto_testDelete(){ //DELETE
        //given Producto
        //when
        Long id = productoRepository.save(producto1).getIdProducto();
        productoRepository.deleteById(id);
        Producto productoEncontrado = productoRepository.findById(id).orElse(null);
        //then
        assertNull(productoEncontrado);

    }

    @Test
    void testFindByNombre() {
        //given Producto
        
        //when
        productoRepository.save(producto1);
        productoRepository.save(producto2);
        productoRepository.save(producto3);
        List<Producto> productoEncontrado = productoRepository.findByNombre("Le").orElse(null);
        //then
        assertNotNull(productoEncontrado);
        assertEquals(2, productoEncontrado.size());
        assertEquals("Leche", productoEncontrado.get(0).getNombre());
        assertEquals(1234.56, productoEncontrado.get(0).getPrecio());
        assertEquals(7, productoEncontrado.get(0).getStock());
        assertEquals("Lechuga", productoEncontrado.get(1).getNombre());
        assertEquals(12.23, productoEncontrado.get(1).getPrecio());
        assertEquals(4, productoEncontrado.get(1).getStock());
    }

    @Test
    void testFindByPrecioANDStock() {
        //given Producto
        
        //when
        productoRepository.save(producto1);
        productoRepository.save(producto2);
        productoRepository.save(producto3);
        List<Producto> productoEncontrado = productoRepository.findByPrecioANDStock(4000D, 4).orElse(null);
        //then
        assertNotNull(productoEncontrado);
        assertEquals(2, productoEncontrado.size());
        assertEquals("Manzana", productoEncontrado.get(0).getNombre());
        assertEquals("Lechuga", productoEncontrado.get(1).getNombre());
    }

    @Test
    void testFindByStock() {
        //given Producto
        
        //when
        productoRepository.save(producto1);
        productoRepository.save(producto2);
        productoRepository.save(producto3);
        List<Producto> productoEncontrado = productoRepository.findByStock().orElse(null);
        //then
        assertNotNull(productoEncontrado);
        assertEquals(3, productoEncontrado.size());
        assertEquals("Leche", productoEncontrado.get(0).getNombre());
        assertEquals("Manzana", productoEncontrado.get(1).getNombre());
        assertEquals("Lechuga", productoEncontrado.get(2).getNombre());
    }
}

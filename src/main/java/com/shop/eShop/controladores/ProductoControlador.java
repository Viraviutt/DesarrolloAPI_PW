package com.shop.eShop.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.eShop.dto.producto.ProductoDTO;
import com.shop.eShop.servicios.ProductoServicio;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/api/v1/productos")
@SuppressWarnings("null")
public class ProductoControlador {
    
    @Autowired
    ProductoServicio productoServicio;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getProductos() {

        log.info("Obteniendo todos los productos");
        HashMap<String, Object> response = new HashMap<>();
        List<ProductoDTO> productos = productoServicio.getAllProductos();
        response.put("productos", productos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getProductoById(@PathVariable("id") Long id) {

        log.info("Obteniendo producto por id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        ProductoDTO producto = productoServicio.getProductoById(id);

        if (producto == null) {

            response.put("error", "El producto no ha sido encontrando");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("producto", producto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<HashMap<String, Object>> getProductosByNombre(@PathVariable("nombre") String nombre) {

        log.info("Obteniendo producto por nombre: " + nombre);
        HashMap<String, Object> response = new HashMap<>();
        List<ProductoDTO> productos = productoServicio.getProductoByNombre(nombre);
        response.put("productos", productos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/instock")
    public ResponseEntity<HashMap<String, Object>> getProductosInStock() {

        log.info("Obteniendo los productos que tengan stock");
        HashMap<String, Object> response = new HashMap<>();
        List<ProductoDTO> producto = productoServicio.getProductoByStockMayorQueCero();
        
        if (producto == null) {

            response.put("error", "El producto no ha sido encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("producto", producto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search?searchTerm1={searchTerm1}&searchTerm2={searchTerm2}")
    public ResponseEntity<HashMap<String, Object>> getProductosByPrecioAndStock(@RequestParam("searchTerm1") Double searchTerm1, @RequestParam("searchTerm2") Integer searchTerm2) {

        log.info("Obteniendo producto por precio " + searchTerm1 + " y stock: " + searchTerm2);
        HashMap<String, Object> response = new HashMap<>();
        List<ProductoDTO> productos = productoServicio.getProductoByPrecioANDStock(searchTerm1, searchTerm2);
        response.put("productos", productos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createProducto(@RequestBody ProductoDTO productoDTO) {

        log.info("Creando el producto: " + productoDTO);
        HashMap<String, Object> response = new HashMap<>();
        ProductoDTO createdProducto = productoServicio.createProducto(productoDTO);
        response.put("producto", createdProducto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateProducto(@PathVariable("id") Long id, @RequestBody ProductoDTO productoDTO) {

        log.info("Actualizando el producto: " + productoDTO);
        HashMap<String, Object> response = new HashMap<>();
        ProductoDTO updatedProducto = productoServicio.updateProducto(id, productoDTO);

        if (updatedProducto == null) {

            response.put("error", "Error actualizando el producto");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("producto", updatedProducto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteProducto(@PathVariable("id") Long id) {

        log.info("Borrando producto por id: " + id);
        HashMap<String, Object> response = new HashMap<>();

        if (productoServicio.deleteProducto(id)) {

            response.put("message", "El producto ha sido borrado");

        } else {

            response.put("error", "Error al borrar el producto");

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
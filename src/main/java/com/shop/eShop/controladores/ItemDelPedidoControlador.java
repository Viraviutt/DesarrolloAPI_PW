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

import com.shop.eShop.dto.itemDelPedido.ItemDelPedidoDTO;
import com.shop.eShop.servicios.ItemDelPedidoServicio;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/itemPedido")
@Slf4j
@SuppressWarnings("null")
public class ItemDelPedidoControlador {

    @Autowired
    private ItemDelPedidoServicio itemDelPedidoServicio;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getPayments() {

        log.info("Obteniendo todos los itemDelPedido");
        HashMap<String, Object> response = new HashMap<>();
        List<ItemDelPedidoDTO> data = itemDelPedidoServicio.getAllItemsDelPedido();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getItemDelPedidoById(@PathVariable("id") Long id) {
        log.info("Obteniendo itemDelPedidos por id: " + id);
        ItemDelPedidoDTO data = itemDelPedidoServicio.getItemDelPedidoById(id);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("pedido/{idPedido}")
    public ResponseEntity<HashMap<String, Object>> getItemDelPedidoByIdPedido(@RequestParam("idPedido") Long idPedido) {
        log.info("Obteniendo itemDelPedidos por id pedido " + idPedido);
        List<ItemDelPedidoDTO> itemDelPedidos = itemDelPedidoServicio.getItemDelPedidoByIdPedido(idPedido);
        if (itemDelPedidos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("itemDelPedidos", itemDelPedidos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("producto/{idProducto}")
    public ResponseEntity<HashMap<String, Object>> getItemDelPedidoByIdProducto(@RequestParam("idProducto") Long idProducto) {
        log.info("Obteniendo itemDelPedidos por id producto " + idProducto);
        List<ItemDelPedidoDTO> itemDelPedidos = itemDelPedidoServicio.getItemDelPedidoByIdProducto(idProducto);
        if (itemDelPedidos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("itemDelPedidos", itemDelPedidos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createItemDelPedido(@RequestBody ItemDelPedidoDTO itemDelPedidoDTO) {

        log.info("Creando el itemDelPedido: " + itemDelPedidoDTO);
        HashMap<String, Object> response = new HashMap<>();
        ItemDelPedidoDTO createdItemDelPedido = itemDelPedidoServicio.createItemDelPedido(itemDelPedidoDTO);
        response.put("itemDelPedido", createdItemDelPedido);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateItemDelPedido(@PathVariable("id") Long id, @RequestBody ItemDelPedidoDTO itemDelPedidoDTO) {

        log.info("Actualizando el itemDelPedido: " + itemDelPedidoDTO);
        HashMap<String, Object> response = new HashMap<>();
        ItemDelPedidoDTO updatedItemDelPedido = itemDelPedidoServicio.updateItemDelPedido(id, itemDelPedidoDTO);

        if (updatedItemDelPedido == null) {

            response.put("error", "Error actualizando el itemDelPedido");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("itemDelPedido", updatedItemDelPedido);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteItemDelPedido(@PathVariable("id") Long id) {

        log.info("Borrando itemDelPedido por id: " + id);
        HashMap<String, Object> response = new HashMap<>();

        if (itemDelPedidoServicio.deleteItemDelPedido(id)) {

            response.put("message", "El itemDelPedido ha sido borrado");

        } else {

            response.put("error", "Error al borrar el itemDelPedido");

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

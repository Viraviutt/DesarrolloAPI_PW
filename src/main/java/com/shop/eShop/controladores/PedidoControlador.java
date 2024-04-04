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

import com.shop.eShop.dto.pedido.PedidoDTO;
import com.shop.eShop.servicios.PedidoServicio;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/api/v1/pedidos")
@SuppressWarnings("null")
public class PedidoControlador {
    
    @Autowired
    PedidoServicio pedidoServicio;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getPedidos() {

        log.info("Obteniendo todos los pedidos");
        HashMap<String, Object> response = new HashMap<>();
        List<PedidoDTO> pedidos = pedidoServicio.getAllPedidos();
        response.put("pedidos", pedidos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getPedidoById(@PathVariable("id") Long id) {

        log.info("Obteniendo pedido por id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        PedidoDTO pedido = pedidoServicio.getPedidoById(id);

        if (pedido == null) {

            response.put("error", "El pedido no ha sido encontrando");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("pedido", pedido);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pedidos/cliente/{idCliente}")
    public ResponseEntity<HashMap<String, Object>> getPedidosByIdCliente(@PathVariable("idCliente") Long idCliente) {

        log.info("Obteniendo pedido por nombre: " + idCliente);
        HashMap<String, Object> response = new HashMap<>();
        List<PedidoDTO> pedidos = pedidoServicio.findByCliente(idCliente);
        response.put("pedidos", pedidos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/date-range?startDate={startDate}&endDate={endDate}")
    public ResponseEntity<HashMap<String, Object>> getPedidosFechaBetween(@RequestParam("startDate") String fechaInicio, @RequestParam("startDate") String fechaFinal) {

        log.info("Obteniendo los pedidos entre fecha: \n" + "Fecha inicial: " + fechaInicio + "\n" + "Fecha final: "+ fechaFinal);
        HashMap<String, Object> response = new HashMap<>();
        List<PedidoDTO> pedidos = pedidoServicio.findByFechaBetween(fechaInicio, fechaFinal); //Insertar fecha en formato yyyy-mm-dd hh:mm:ss
        
        if (pedidos == null) {

            response.put("error", "El pedido no ha sido encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("pedido", pedidos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createPedido(@RequestBody PedidoDTO pedidoDTO) {

        log.info("Creando el pedido: " + pedidoDTO);
        HashMap<String, Object> response = new HashMap<>();
        PedidoDTO createdPedido = pedidoServicio.createPedido(pedidoDTO);
        response.put("pedido", createdPedido);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updatePedido(@PathVariable("id") Long id, @RequestBody PedidoDTO pedidoDTO) {

        log.info("Actualizando el pedido: " + pedidoDTO);
        HashMap<String, Object> response = new HashMap<>();
        PedidoDTO updatedPedido = pedidoServicio.updatePedido(id, pedidoDTO);

        if (updatedPedido == null) {

            response.put("error", "Error actualizando el pedido");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("pedido", updatedPedido);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deletePedido(@PathVariable("id") Long id) {

        log.info("Borrando pedido por id: " + id);
        HashMap<String, Object> response = new HashMap<>();

        if (pedidoServicio.deletePedido(id)) {

            response.put("message", "El pedido ha sido borrado");

        } else {

            response.put("error", "Error al borrar el pedido");

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

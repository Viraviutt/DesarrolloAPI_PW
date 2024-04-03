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
import org.springframework.web.bind.annotation.RestController;

import com.shop.eShop.dto.cliente.ClienteDTO;
import com.shop.eShop.servicios.ClienteServicio;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/api/v1/clientes")
@SuppressWarnings("null")
public class ClienteControlador {
    
    @Autowired
    ClienteServicio clienteServicio;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getClientes() {

        log.info("Obteniendo todos los clientes");
        HashMap<String, Object> response = new HashMap<>();
        List<ClienteDTO> clientes = clienteServicio.getAllClientes();
        response.put("clientes", clientes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getClienteById(@PathVariable("id") Long id) {

        log.info("Obteniendo cliente por id: " + id);
        HashMap<String, Object> response = new HashMap<>();
        ClienteDTO cliente = clienteServicio.getClienteById(id);

        if (cliente == null) {

            response.put("error", "El cliente no ha sido encontrando");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("cliente", cliente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<HashMap<String, Object>> getClientesByNombre(@PathVariable("nombre") String nombre) {

        log.info("Obteniendo cliente por nombre: " + nombre);
        HashMap<String, Object> response = new HashMap<>();
        List<ClienteDTO> clientes = clienteServicio.getClienteByNombre(nombre);
        response.put("clientes", clientes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<HashMap<String, Object>> getClientesByEmail(@PathVariable("correo") String correo) {

        log.info("Obteniendo cliente por correo: " + correo);
        HashMap<String, Object> response = new HashMap<>();
        List<ClienteDTO> cliente = clienteServicio.getClienteByEmail(correo);
        
        if (cliente == null) {

            response.put("error", "El cliente no ha sido encontrado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("cliente", cliente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/direccion/{direccion}")
    public ResponseEntity<HashMap<String, Object>> getClientesByDireccion(@PathVariable("direccion") String direccion) {

        log.info("Obteniendo cliente por direccion: " + direccion);
        HashMap<String, Object> response = new HashMap<>();
        List<ClienteDTO> clientes = clienteServicio.getClienteByDireccion(direccion);
        response.put("clientes", clientes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createCliente(@RequestBody ClienteDTO clienteDTO) {

        log.info("Creando el cliente: " + clienteDTO);
        HashMap<String, Object> response = new HashMap<>();
        ClienteDTO createdCliente = clienteServicio.createCliente(clienteDTO);
        response.put("cliente", createdCliente);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateCliente(@PathVariable("id") Long id, @RequestBody ClienteDTO clienteDTO) {

        log.info("Actualizando el cliente: " + clienteDTO);
        HashMap<String, Object> response = new HashMap<>();
        ClienteDTO updatedCliente = clienteServicio.updateCliente(id, clienteDTO);

        if (updatedCliente == null) {

            response.put("error", "Error actualizando el cliente");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("cliente", updatedCliente);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteCliente(@PathVariable("id") Long id) {

        log.info("Borrando cliente por id: " + id);
        HashMap<String, Object> response = new HashMap<>();

        if (clienteServicio.deleteCliente(id)) {

            response.put("message", "El cliente ha sido borrado");

        } else {

            response.put("error", "Error al borrar el cliente");

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
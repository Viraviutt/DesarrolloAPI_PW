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

import com.shop.eShop.dto.detalleEnvio.DetalleEnvioDTO;
import com.shop.eShop.servicios.DetalleEnvioServicio;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/detalleEnvio")
@Slf4j
@SuppressWarnings("null")
public class DetalleEnvioControlador {

    @Autowired
    private DetalleEnvioServicio detalleEnvioServicio;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getPayments() {

        log.info("Obteniendo todos los detalleEnvio");
        HashMap<String, Object> response = new HashMap<>();
        List<DetalleEnvioDTO> data = detalleEnvioServicio.getAllDetalleEnvios();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getDetalleEnvioById(@PathVariable("id") Long id) {
        log.info("Obteniendo detalleEnvio por id: " + id);
        DetalleEnvioDTO data = detalleEnvioServicio.getDetalleEnvioById(id);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<HashMap<String, Object>> getDetalleEnviosByIdPedido(@PathVariable("idPedido") Long idPedido) {

        log.info("Obteniendo detalleEnvio por id del pedido: " + idPedido);
        HashMap<String, Object> response = new HashMap<>();
        DetalleEnvioDTO detalleEnvios = detalleEnvioServicio.getDetalleEnvioByIdPedido(idPedido);
        response.put("detalleEnvios", detalleEnvios);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/carrier?name={carrierName}")
    public ResponseEntity<HashMap<String, Object>> getDetalleEnvioFechaBetween(@RequestParam("carrierName") String transportadora) {
        log.info("Obteniendo detalleEnvios por nombre de transportadora" + transportadora);
        List<DetalleEnvioDTO> detalleEnvios = detalleEnvioServicio.getDetalleEnvioByTransportadora(transportadora); //Insertar fecha en formato yyyy-mm-dd hh:mm:ss
        if (detalleEnvios == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("detalleEnvios", detalleEnvios);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createDetalleEnvio(@RequestBody DetalleEnvioDTO detalleEnvioDTO) {

        log.info("Creando el detalleEnvio: " + detalleEnvioDTO);
        HashMap<String, Object> response = new HashMap<>();
        DetalleEnvioDTO createdDetalleEnvio = detalleEnvioServicio.createDetalleEnvio(detalleEnvioDTO);
        response.put("detalleEnvio", createdDetalleEnvio);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updateDetalleEnvio(@PathVariable("id") Long id, @RequestBody DetalleEnvioDTO detalleEnvioDTO) {

        log.info("Actualizando el detalleEnvio: " + detalleEnvioDTO);
        HashMap<String, Object> response = new HashMap<>();
        DetalleEnvioDTO updatedDetalleEnvio = detalleEnvioServicio.updateDetalleEnvio(id, detalleEnvioDTO);

        if (updatedDetalleEnvio == null) {

            response.put("error", "Error actualizando el detalleEnvio");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("detalleEnvio", updatedDetalleEnvio);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteDetalleEnvio(@PathVariable("id") Long id) {

        log.info("Borrando detalleEnvio por id: " + id);
        HashMap<String, Object> response = new HashMap<>();

        if (detalleEnvioServicio.deleteDetalleEnvio(id)) {

            response.put("message", "El detalleEnvio ha sido borrado");

        } else {

            response.put("error", "Error al borrar el detalleEnvio");

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

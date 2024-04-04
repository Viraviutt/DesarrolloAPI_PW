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

import com.shop.eShop.dto.pago.PagoDTO;
import com.shop.eShop.servicios.PagoServicio;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/pagos")
@Slf4j
@SuppressWarnings("null")
public class PagoControlador {
    @Autowired
    private PagoServicio pagoServicio;

    @GetMapping("")
    public ResponseEntity<HashMap<String, Object>> getPayments() {

        log.info("Obteniendo todos los pagos");
        HashMap<String, Object> response = new HashMap<>();
        List<PagoDTO> data = pagoServicio.getAllPagos();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getPagoById(@PathVariable("id") Long id) {
        log.info("Obteniendo pagos por id: " + id);
        PagoDTO data = pagoServicio.getPagoById(id);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/date-range?startDate={startDate}&endDate={endDate}")
    public ResponseEntity<HashMap<String, Object>> getPagoFechaBetween(@RequestParam("startDate") String fechaInicio, @RequestParam("startDate") String fechaFinal) {
        log.info("Obteniendo pagos entre fecha: \n" + "Fecha inicial: " + fechaInicio + "\n" + "Fecha final: "+ fechaFinal);
        List<PagoDTO> pagos = pagoServicio.findByFechaBetween(fechaInicio, fechaFinal); //Insertar fecha en formato yyyy-mm-dd hh:mm:ss
        if (pagos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("pagos", pagos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<HashMap<String, Object>> createPago(@RequestBody PagoDTO pagoDTO) {

        log.info("Creando el pago: " + pagoDTO);
        HashMap<String, Object> response = new HashMap<>();
        PagoDTO createdPago = pagoServicio.createPago(pagoDTO);
        response.put("pago", createdPago);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> updatePago(@PathVariable("id") Long id, @RequestBody PagoDTO pagoDTO) {

        log.info("Actualizando el pago: " + pagoDTO);
        HashMap<String, Object> response = new HashMap<>();
        PagoDTO updatedPago = pagoServicio.updatePago(id, pagoDTO);

        if (updatedPago == null) {

            response.put("error", "Error actualizando el pago");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        response.put("pago", updatedPago);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deletePago(@PathVariable("id") Long id) {

        log.info("Borrando pago por id: " + id);
        HashMap<String, Object> response = new HashMap<>();

        if (pagoServicio.deletePago(id)) {

            response.put("message", "El pago ha sido borrado");

        } else {

            response.put("error", "Error al borrar el pago");

        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}

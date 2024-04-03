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
public class PagoControlador {
    @Autowired
    private PagoServicio pagoServicio;

    @GetMapping("") 
    public ResponseEntity<HashMap<String, Object>> getPayments() {

        log.info("Obteniendo el pago de todos los items");
        HashMap<String, Object> response = new HashMap<>();
        List<PagoDTO> data = pagoServicio.getAllPagos();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getPagoById(@PathVariable("id") Long id) {
        log.info("Obteniendo pagos de items por id: " + id);
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
        PagoDTO data = pagoServicio.getPagoById(null);
        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

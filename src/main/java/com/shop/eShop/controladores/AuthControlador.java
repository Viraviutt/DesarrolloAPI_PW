package com.shop.eShop.controladores;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.eShop.dto.cliente.ClienteDTO;
import com.shop.eShop.servicios.ClienteServicio;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthControlador {

    @Autowired
    ClienteServicio clienteServicio;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt-secret}") private String jwtSecret;

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, Object>> createCustomer(@RequestBody ClienteDTO clienteDTO) {
        log.info("Creating customer: " + clienteDTO);
        HashMap<String, Object> response = new HashMap<>();
        String encryptedPassword = passwordEncoder.encode(clienteDTO.getPassword());
        clienteDTO.setPassword(encryptedPassword);
        ClienteDTO res = clienteServicio.createCliente(clienteDTO);
        if (res == null) {
            response.put("error", "Error creating customer");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("data", "Customer created");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> authenticateUser(@RequestBody ClienteDTO clienteDTO) {

        ClienteDTO existingCustomer = clienteServicio.getClienteByCorreo(clienteDTO.correo());

        if (existingCustomer == null
                || !passwordEncoder.matches(clienteDTO.getPassword(), existingCustomer.getPassword())) {
            HashMap<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                existingCustomer.getCorreo(),
                existingCustomer.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtSecret.generateToken(authentication);

        HashMap<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }
}

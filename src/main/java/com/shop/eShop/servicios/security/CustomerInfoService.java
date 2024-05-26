package com.shop.eShop.servicios.security;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.eShop.repositories.ClienteRepository;
import com.shop.eShop.entities.Cliente;

public class CustomerInfoService {
    private final ClienteRepository userRepository;

    public CustomerInfoService(ClienteRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException{
        Optional<Cliente> userDetails = userRepository.findByCorreoCompleto(correo);
        return userDetails.map(ClienteInfoDetail::new).orElseThrow(()-> new UsernameNotFoundException("User not found."));
    }
}

package com.sistema.crmsimples.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.crmsimples.model.Clientes;
import com.sistema.crmsimples.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Clientes salvar(Clientes cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional(rollbackOn= Exception.class)
    public List<Clientes> listarTodos() {
        return  clienteRepository.findAll();
    }
    
    @Transactional(rollbackOn= Exception.class)
    public Optional<Clientes> listarPorId(UUID id) {
        return clienteRepository.findById(id);
    }
    

}

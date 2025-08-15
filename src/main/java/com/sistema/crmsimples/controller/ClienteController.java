package com.sistema.crmsimples.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.crmsimples.model.Clientes;
import com.sistema.crmsimples.service.ClienteService;


@RestController
@RequestMapping("/crm")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Clientes> criar(@RequestBody Clientes cliente) {

        Clientes salvarCliente = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarCliente);
        
    }

    @GetMapping()
    public ResponseEntity <List<Clientes>> listarClientes() {

        List<Clientes> clientes = clienteService.listarTodos();
        return ResponseEntity.ok(clientes);
    }
    
    

}

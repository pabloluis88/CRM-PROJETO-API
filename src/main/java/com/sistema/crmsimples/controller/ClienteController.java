package com.sistema.crmsimples.controller;

import java.util.List;
import java.util.UUID;

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

import com.sistema.crmsimples.model.Clientes;
import com.sistema.crmsimples.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /**
     * CREATE: POST /clientes
     */
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Clientes cliente) {
        try {
            Clientes salvarCliente = clienteService.salvar(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvarCliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("erro", "Erro interno do servidor"));
        }
    }

    /**
     * READ: GET /clientes (todos os clientes)
     */
    @GetMapping
    public ResponseEntity<List<Clientes>> listarClientes(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String nome) {
        
        try {
            List<Clientes> clientes;
            
            if (status != null || nome != null) {
                // Busca com filtros
                clientes = clienteService.buscarComFiltros(status, nome);
            } else {
                // Busca todos
                clientes = clienteService.listarTodos();
            }
            
            return ResponseEntity.ok(clientes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * READ: GET /clientes/{id} (cliente específico)
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> listarClientePorId(@PathVariable UUID id) {
        try {
            return clienteService.listarPorId(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro interno do servidor"));
        }
    }

    /**
     * UPDATE: PUT /clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, 
                                     @Valid @RequestBody Clientes cliente) {
        try {
            Clientes clienteAtualizado = clienteService.atualizar(id, cliente);
            return ResponseEntity.ok(clienteAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro interno do servidor"));
        }
    }

    /**
     * DELETE: DELETE /clientes/{id} (exclusão lógica)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable UUID id) {
        try {
            clienteService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro interno do servidor"));
        }
    }

    // Classe auxiliar para retornar mensagens de erro em formato JSON
    private static class Map {
        public static java.util.Map<String, String> of(String key, String value) {
            return java.util.Map.of(key, value);
        }
    }
}
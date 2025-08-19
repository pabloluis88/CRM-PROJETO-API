package com.sistema.crmsimples.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.crmsimples.model.Clientes;

@Repository
public interface ClienteRepository extends JpaRepository<Clientes, UUID> {

    /**
     * Verifica se existe cliente com o CPF informado
     */
    boolean existsByCpf(String cpf);
    
    /**
     * Verifica se existe cliente com o email informado
     */
    boolean existsByEmail(String email);
    
    /**
     * Verifica se existe cliente com o email informado, exceto o cliente com o ID especificado
     * (usado para validação na atualização)
     */
    boolean existsByEmailAndIdNot(String email, UUID id);
}
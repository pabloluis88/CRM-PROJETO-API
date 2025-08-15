package com.sistema.crmsimples.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.crmsimples.model.Clientes;

public interface ClienteRepository extends JpaRepository<Clientes, UUID> {

    

}

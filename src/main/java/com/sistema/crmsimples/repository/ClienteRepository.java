package com.sistema.crmsimples.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.crmsimples.model.Clientes;

@Repository
public interface ClienteRepository extends JpaRepository<Clientes, UUID> {

    

}

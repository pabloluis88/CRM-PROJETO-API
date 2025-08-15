package com.sistema.crmsimples.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
public class Clientes {

@Id
@GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
private UUID id;	                    //PK	Auto incremento

@NotNull(message = "nome não pode ser nulo")
@Size(min=3, message= "Obrigatório, tamanho minimo 3 caracteres")
private String nome;	//VARCHAR	Obrigatório, mínimo 3 caracteres

@Email
private String email;	//VARCHAR	Obrigatório, único

private String telefone;	//VARCHAR	Opcional, validação DDI+DDD se informado validar na service

private String cpf;      //VARCHAR	Obrigatório, único, formato e dígitos válidos validar na service

private String status;	//VARCHAR(20)	ATIVO, INATIVO ou PROSPECT

@CreationTimestamp
private LocalDateTime criado_em;	//DATETIME	Definido no momento da criação

private LocalDateTime atualizado_em;	//DATETIME	Atualizado a cada alteração

}

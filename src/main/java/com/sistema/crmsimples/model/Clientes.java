package com.sistema.crmsimples.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "clientes")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Clientes {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Nome não pode ser nulo")
    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    private String nome;

    @Email(message = "Email deve ter formato válido")
    @NotNull(message = "Email não pode ser nulo")
    @NotBlank(message = "Email não pode estar em branco")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,3}\\s?)?\\(?\\d{2}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$|^$", 
             message = "Telefone deve seguir o formato: +55 (11) 99999-9999 ou similar")
    private String telefone;

    @NotNull(message = "CPF não pode ser nulo")
    @NotBlank(message = "CPF não pode estar em branco")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}", 
             message = "CPF deve estar no formato 000.000.000-00 ou conter 11 dígitos")
    @Column(unique = true)
    private String cpf;

    @Pattern(regexp = "ATIVO|INATIVO|PROSPECT", 
             message = "Status deve ser: ATIVO, INATIVO ou PROSPECT")
    @Column(length = 8)
    private String status;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    private void prePersist() {
        if (status == null || status.trim().isEmpty()) {
            status = "PROSPECT";
        }
        // Normalizar CPF removendo pontos e traços para armazenamento
        if (cpf != null) {
            cpf = cpf.replaceAll("[^\\d]", "");
        }
    }
}
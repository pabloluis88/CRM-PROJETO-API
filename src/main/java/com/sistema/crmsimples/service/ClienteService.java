package com.sistema.crmsimples.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.sistema.crmsimples.model.Clientes;
import com.sistema.crmsimples.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional(rollbackOn = Exception.class)
    public Clientes salvar(Clientes cliente) {
        // Validar CPF antes de salvar
        if (!isValidCPF(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }

        // Normalizar CPF para armazenamento (somente números)
        cliente.setCpf(cliente.getCpf().replaceAll("[^\\d]", ""));

        // Verificar se CPF já existe
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        // Verificar se email já existe
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // Definir status padrão se não informado
        if (cliente.getStatus() == null || cliente.getStatus().trim().isEmpty()) {
            cliente.setStatus("PROSPECT");
        }

        return clienteRepository.save(cliente);
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Clientes> listarTodos() {
        return clienteRepository.findAll();
    }
    
    @Transactional(rollbackOn = Exception.class)
    public Optional<Clientes> listarPorId(UUID id) {
        return clienteRepository.findById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Clientes> buscarComFiltros(String status, String nome) {
        Clientes exemplo = new Clientes();
        
        if (status != null && !status.trim().isEmpty()) {
            exemplo.setStatus(status);
        }
        
        if (nome != null && !nome.trim().isEmpty()) {
            exemplo.setNome(nome);
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Clientes> example = Example.of(exemplo, matcher);
        return clienteRepository.findAll(example);
    }

    @Transactional(rollbackOn = Exception.class)
    public Clientes atualizar(UUID id, Clientes clienteAtualizado) {
        Optional<Clientes> clienteExistente = clienteRepository.findById(id);
        
        if (clienteExistente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        Clientes cliente = clienteExistente.get();
        
        // Impedir alteração do CPF
        if (clienteAtualizado.getCpf() != null && 
            !cliente.getCpf().equals(clienteAtualizado.getCpf().replaceAll("[^\\d]", ""))) {
            throw new IllegalArgumentException("CPF não pode ser alterado");
        }

        // Verificar se email já existe em outro cliente
        if (clienteAtualizado.getEmail() != null && 
            !cliente.getEmail().equals(clienteAtualizado.getEmail())) {
            if (clienteRepository.existsByEmailAndIdNot(clienteAtualizado.getEmail(), id)) {
                throw new IllegalArgumentException("Email já cadastrado para outro cliente");
            }
            cliente.setEmail(clienteAtualizado.getEmail());
        }

        // Atualizar campos permitidos
        if (clienteAtualizado.getNome() != null) {
            cliente.setNome(clienteAtualizado.getNome());
        }
        
        if (clienteAtualizado.getTelefone() != null) {
            cliente.setTelefone(clienteAtualizado.getTelefone());
        }
        
        if (clienteAtualizado.getStatus() != null) {
            cliente.setStatus(clienteAtualizado.getStatus());
        }

        // atualizado_em será definido automaticamente pelo @UpdateTimestamp
        return clienteRepository.save(cliente);
    }

    @Transactional(rollbackOn = Exception.class)
    public void excluir(UUID id) {
        Optional<Clientes> cliente = clienteRepository.findById(id);
        
        if (cliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        // Exclusão lógica - alterar status para INATIVO
        Clientes clienteParaInativar = cliente.get();
        clienteParaInativar.setStatus("INATIVO");
        clienteParaInativar.setAtualizadoEm(LocalDateTime.now());
        
        clienteRepository.save(clienteParaInativar);
    }

    /**
     * Validação completa de CPF incluindo dígitos verificadores
     */
    private boolean isValidCPF(String cpf) {
        if (cpf == null) return false;
        
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^\\d]", "");
        
        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) return false;
        
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        // Calcula os dígitos verificadores
        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
            }
            
            // Calcula primeiro dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += digits[i] * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) firstDigit = 0;
            
            // Calcula segundo dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += digits[i] * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) secondDigit = 0;
            
            // Verifica se os dígitos calculados coincidem
            return digits[9] == firstDigit && digits[10] == secondDigit;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
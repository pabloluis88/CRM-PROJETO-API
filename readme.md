# CRM Simples - Sistema de Gestão de Clientes

Um sistema CRM (Customer Relationship Management) simples para cadastro e gestão de clientes pessoa física, desenvolvido em Java com Spring Boot.

## 📋 Funcionalidades

- ✅ Cadastro completo de clientes com validações rigorosas
- ✅ Consulta de clientes com filtros por status e nome
- ✅ Atualização de dados com regras de negócio
- ✅ Exclusão lógica (soft delete) via alteração de status
- ✅ Validação completa de CPF com dígitos verificadores
- ✅ Validação de formato de telefone com DDI/DDD
- ✅ Controle de unicidade para CPF e email
- ✅ Auditoria automática (created_at, updated_at)

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Jakarta Validation**
- **Hibernate**

## 📊 Modelo de Dados

### Tabela: `clientes`

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | UUID | Chave primária (auto-gerada) |
| `nome` | VARCHAR | Nome completo (obrigatório, min 3 chars) |
| `email` | VARCHAR | Email (obrigatório, único, formato válido) |
| `telefone` | VARCHAR | Telefone (opcional, validação DDI+DDD) |
| `cpf` | VARCHAR | CPF (obrigatório, único, dígitos válidos) |
| `status` | VARCHAR(20) | Status do cliente (ATIVO, INATIVO, PROSPECT) |
| `criado_em` | TIMESTAMP | Data/hora de criação (automático) |
| `atualizado_em` | TIMESTAMP | Data/hora da última atualização (automático) |

## 🛠️ Instalação e Configuração

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL 12+

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd crmsimples
```

### 2. Configure o banco de dados

Crie um banco PostgreSQL e configure as credenciais:

```sql
-- Criar database
CREATE DATABASE crm;

-- Criar usuário
CREATE USER crm_user WITH PASSWORD 'crm_pass';

-- Conceder permissões
GRANT ALL PRIVILEGES ON DATABASE crm TO crm_user;
```

### 3. Configure as propriedades

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Configuração do banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/crm
spring.datasource.username=crm_user
spring.datasource.password=crm_pass

# Configuração JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Execute a aplicação

```bash
# Compilar e executar
mvn spring-boot:run

# Ou compilar e executar o JAR
mvn clean package
java -jar target/crmsimples-0.0.1-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 API Endpoints

### Base URL: `http://localhost:8080`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/clientes` | Criar novo cliente |
| `GET` | `/clientes` | Listar todos os clientes |
| `GET` | `/clientes?status=ATIVO` | Listar clientes por status |
| `GET` | `/clientes?nome=João` | Buscar clientes por nome |
| `GET` | `/clientes/{id}` | Buscar cliente por ID |
| `PUT` | `/clientes/{id}` | Atualizar cliente |
| `DELETE` | `/clientes/{id}` | Excluir cliente (soft delete) |

## 💡 Exemplos de Uso

### 1. Criar Cliente

```bash
POST /clientes
Content-Type: application/json

{
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "telefone": "+55 (11) 99999-9999",
    "cpf": "123.456.789-09",
    "status": "ATIVO"
}
```

**Resposta (201 Created):**
```json
{
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "telefone": "+55 (11) 99999-9999",
    "cpf": "12345678909",
    "status": "ATIVO",
    "criadoEm": "2025-01-15T10:30:00",
    "atualizadoEm": "2025-01-15T10:30:00"
}
```

### 2. Listar Clientes com Filtros

```bash
GET /clientes?status=ATIVO&nome=João
```

### 3. Atualizar Cliente

```bash
PUT /clientes/{id}
Content-Type: application/json

{
    "nome": "João Silva Santos",
    "telefone": "+55 (11) 88888-8888"
}
```

### 4. Excluir Cliente (Soft Delete)

```bash
DELETE /clientes/{id}
```

O cliente não será removido do banco, apenas terá seu status alterado para `INATIVO`.

## ⚠️ Regras de Validação

### CPF
- **Formato aceito:** `123.456.789-09` ou `12345678909`
- **Validação:** Dígitos verificadores obrigatórios
- **Unicidade:** Não pode haver CPFs duplicados
- **Imutabilidade:** CPF não pode ser alterado após criação

### Email
- **Formato:** Deve ser um email válido
- **Obrigatório:** Campo obrigatório
- **Unicidade:** Não pode haver emails duplicados

### Telefone
- **Formato:** `+55 (11) 99999-9999`, `(11) 99999-9999`, `11999999999`
- **Validação:** DDI + DDD + número válido
- **Opcional:** Campo não obrigatório

### Status
- **Valores aceitos:** `ATIVO`, `INATIVO`, `PROSPECT`
- **Padrão:** `PROSPECT` (se não informado)

### Nome
- **Obrigatório:** Campo obrigatório
- **Tamanho mínimo:** 3 caracteres

## 🔧 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/sistema/crmsimples/
│   │   ├── config/
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── controller/
│   │   │   └── ClienteController.java
│   │   ├── model/
│   │   │   └── Clientes.java
│   │   ├── repository/
│   │   │   └── ClienteRepository.java
│   │   ├── service/
│   │   │   └── ClienteService.java
│   │   └── CrmsimplesApplication.java
│   └── resources/
│       └── application.properties
```

## 🧪 Testes

### CPFs válidos para teste:
- `123.456.789-09`
- `111.444.777-35`
- `000.000.001-91`

### CPFs inválidos:
- `123.456.789-00` (dígito verificador inválido)
- `111.111.111-11` (todos os dígitos iguais)
- `123.456.789` (formato incompleto)

## 🚨 Tratamento de Erros

A API retorna respostas estruturadas para diferentes tipos de erro:

### Erro de Validação (400 Bad Request)
```json
{
    "erro": "Dados inválidos",
    "detalhes": {
        "cpf": "CPF deve estar no formato 000.000.000-00",
        "email": "Email deve ter formato válido"
    }
}
```

### Erro de Regra de Negócio (400 Bad Request)
```json
{
    "erro": "CPF já cadastrado"
}
```

### Cliente não encontrado (404 Not Found)
```json
{
    "erro": "Cliente não encontrado"
}
```

## 📝 Logs

A aplicação gera logs detalhados das operações SQL:
- **SQL queries** são logadas no console
- **Formatação** das queries para melhor legibilidade
- **Timezone** configurado para UTC

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Contato

- **Autor:** [Pablo Leite]
- **Email:** [pabloleite@outlook.com]
- **LinkedIn:** [https://www.linkedin.com/in/pabloleiteti]

---

**Versão:** 1.0.0  
**Última atualização:** Janeiro 2025
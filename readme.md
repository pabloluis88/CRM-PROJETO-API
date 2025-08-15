📇 Gestão de Clientes (CRM Simples)

Sistema de cadastro e gestão de clientes (pessoa física) com informações de contato e status, oferecendo operações de CRUD com regras de validação.

📌 Contexto

Este projeto implementa uma API REST para gerenciar clientes, permitindo criar, consultar, atualizar e inativar registros.
O sistema mantém as informações consistentes e garante integridade de dados através de validações específicas.

🗄 Estrutura da Tabela cliente
Campo	Tipo	Restrições
id	PK	Auto incremento
nome	VARCHAR	Obrigatório, mínimo 3 caracteres
email	VARCHAR	Obrigatório, único
telefone	VARCHAR	Opcional, validação DDI+DDD se informado
cpf	VARCHAR	Obrigatório, único, formato e dígitos válidos
status	VARCHAR(20)	ATIVO, INATIVO ou PROSPECT
criado_em	DATETIME	Definido no momento da criação
atualizado_em	DATETIME	Atualizado a cada alteração
📍 Endpoints da API
Criar Cliente

POST /clientes

Body (JSON):

{
  "nome": "João da Silva",
  "email": "joao@email.com",
  "telefone": "+55 11 91234-5678",
  "cpf": "12345678909",
  "status": "ATIVO"
}

Consultar Cliente por ID

GET /clientes/{id}

Listar Clientes com Filtro

GET /clientes?status=ATIVO&nome=João

Atualizar Cliente

PUT /clientes/{id}

Observação: não é permitido alterar o cpf.

Excluir Cliente (Lógico)

DELETE /clientes/{id}

Ação: atualiza o campo status para INATIVO.

✅ Regras de Negócio

CPF válido (formato e dígitos verificadores).

Email obrigatório e único.

Status deve ser um dos valores: ATIVO, INATIVO, PROSPECT.

Nome obrigatório, mínimo 3 caracteres.

Telefone opcional, mas se informado deve ser válido no formato DDI+DDD.

Exclusão lógica → altera status para INATIVO (não remove do banco).

Não permitir duplicação de cpf ou email.

Proibido atualizar o CPF após criação.

atualizado_em deve ser modificado sempre que houver alteração de dados.

🛠 Tecnologias Sugeridas

Java / Spring Boot ou .NET Core

Banco de Dados: PostgreSQL ou MySQL

ORM: JPA / Hibernate ou Entity Framework

Validações: Bean Validation (Java) ou Data Annotations (.NET)

Testes: JUnit / xUnit

Documentação: Swagger / OpenAPI

▶️ Como Executar

Clonar o repositório

git clone https://github.com/seu-usuario/gestao-clientes.git
cd gestao-clientes


Configurar o banco de dados no arquivo de configuração (application.properties ou appsettings.json).

Instalar dependências

# Java (Maven)
mvn install

# Java
mvn spring-boot:run



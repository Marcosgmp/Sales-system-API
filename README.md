# Sales System API

API REST desenvolvida em **Spring Boot** para gerenciamento de vendas, usuários, produtos e carrinho de compras, com **autenticação JWT** e **controle de acesso por roles (USER / ADMIN)**.

Projeto criado com foco em **boas práticas**, **arquitetura em camadas** e **organização profissional**, sendo ideal para portfólio backend.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- Spring Web
- Spring Security
- Spring Data JPA (Hibernate)
- JWT (JSON Web Token)
- BCrypt Password Encoder
- PostgreSQL / MySQL
- Maven

---

## 🧱 Arquitetura do Sistema

O sistema segue o padrão **arquitetura em camadas**, garantindo separação de responsabilidades, manutenibilidade e escalabilidade.

### Camadas

- **Controller**: expõe os endpoints REST e recebe as requisições HTTP.
- **Service**: contém a lógica de negócio da aplicação.
- **Repository**: acesso e persistência de dados via JPA/Hibernate.
- **Entity**: mapeamento das tabelas do banco de dados.
- **DTO**: objetos para entrada e saída de dados (Request / Response).
- **Security**: autenticação, autorização e configuração de segurança com JWT.

---

## 📁 Estrutura de Pastas

```
src/
├── main/
│   ├── java/
│   │   └── com.sales.system/
│   │       ├── controller/
│   │       │   ├── admin/
│   │       │   ├── auth/
│   │       │   └── user/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── repository/
│   │       ├── security/
│   │       ├── service/
│   │       └── SystemApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

---

## 🔐 Segurança e Autenticação

A aplicação utiliza **JWT (JSON Web Token)** para autenticação stateless.

### Fluxo de Autenticação

1. Usuário realiza login via `/api/auth/login`
2. Credenciais são validadas
3. Um **JWT** é gerado e retornado
4. O token deve ser enviado no header:

```
Authorization: Bearer <token>
```

### Controle de Acesso

- **Rotas públicas**:
  - `/api/auth/**`
  - `/api/products/**`

- **Rotas protegidas**:
  - `/api/admin/**` → apenas `ROLE_ADMIN`
  - Demais rotas → usuário autenticado

---

## 👥 Roles do Sistema

- **USER**: acesso a funcionalidades básicas (produtos, carrinho, perfil)
- **ADMIN**: gerenciamento de usuários, produtos, roles e carrinho

---

## 🗄️ Modelo de Dados (Resumo)

### Principais Entidades

- **User**
- **Roles**
- **UserRole** (tabela associativa)
- **Product**
- **Cart**
- **CartItem**
- **Address**

### Relacionamentos

- User **1:N** Cart
- Cart **1:N** CartItem
- Product **1:N** CartItem
- User **N:N** Roles (via UserRole)
- User **1:1** Address

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL ou MySQL

### Configuração de ambiente

Edite o arquivo `application.properties` e defina as variáveis de ambiente:

- DB_URL
- DB_USER
- DB_PASSWORD

### Passos

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/seu-repositorio.git

# Entrar no projeto
cd sales-system

# Rodar a aplicação
mvn spring-boot:run
```

A API estará disponível em:

```
http://localhost:8080
```

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais e portfólio profissional.

---

## 👤 Autor

**Marcos Gustavo Mendonça Pereira**  
Estudante de Ciência da Computação (IFCE)  
Backend Developer

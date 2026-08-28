# E-commerce Build & Run

API REST de um e-commerce simples, desenvolvida com **Spring Boot** e **PostgreSQL**, com suporte a cadastro de usuários, produtos com tags e criação/consulta de pedidos.

## Tecnologias

- Java 21
- Spring Boot 4.1.0 (Web, Data JPA)
- PostgreSQL
- Maven (com Maven Wrapper)
- Docker / Docker Compose (banco de dados)

## Estrutura do projeto

```
src/main/java/tech/oliver/ecommerce_build/run
├── Application.java
├── controller/          # Endpoints REST (Users, Orders) e DTOs
├── entities/             # Entidades JPA (User, Product, Tag, Order, OrderItem, BillingAddress)
├── exception/            # Exceções de negócio (CreateOrderException)
├── repository/           # Repositórios Spring Data JPA
└── service/               # Regras de negócio (UserService, OrderService)

src/main/resources
├── application.properties
└── data.sql               # Dados iniciais (produtos e tags)

docker/
└── docker-compose.yml     # Sobe o PostgreSQL
```

## Modelo de dados

- **User**: possui um `BillingAddress` (endereço de cobrança).
- **Product**: possui uma lista de `Tag` (many-to-many).
- **Order**: pertence a um `User` e possui uma lista de `OrderItem`.
- **OrderItem**: chave composta (`order_id` + `product_id`), guarda quantidade e preço de venda no momento da compra.

## Pré-requisitos

- JDK 21
- Docker e Docker Compose (para o banco de dados)
- Não é necessário instalar o Maven — o projeto inclui o Maven Wrapper (`mvnw`)

## Como executar

### 1. Subir o banco de dados

```bash
cd docker
docker compose up -d
```

Isso cria um container PostgreSQL com:
- Banco: `ecommercedb`
- Usuário: `myuser`
- Senha: `secret`
- Porta: `5432`

### 2. Rodar a aplicação

Na raiz do projeto:

```bash
# Linux/macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

A aplicação sobe por padrão em `http://localhost:8080`.

> Ao iniciar, o Hibernate atualiza o schema automaticamente (`ddl-auto=update`) e o `data.sql` popula produtos e tags de exemplo.

### 3. Rodar os testes

```bash
./mvnw test
```

## Configuração

As configurações de conexão com o banco ficam em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommercedb
spring.datasource.username=myuser
spring.datasource.password=secret
```

Ajuste esses valores caso altere o `docker-compose.yml` ou aponte para outro banco.

## Endpoints da API

### Usuários (`/users`)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/users` | Cria um usuário |
| `GET` | `/users/{userId}` | Busca um usuário por ID |
| `DELETE` | `/users/{userId}` | Remove um usuário |

**Exemplo de criação de usuário:**

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "João Silva",
    "address": "Rua das Flores",
    "number": "123",
    "complement": "Apto 45"
  }'
```

### Pedidos (`/orders`)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/orders` | Cria um pedido |
| `GET` | `/orders?page=0&pageSize=10` | Lista pedidos paginados |
| `GET` | `/orders/{orderId}` | Busca um pedido por ID |

**Exemplo de criação de pedido:**

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "uuid-do-usuario",
    "items": [
      { "productId": 1, "quantity": 2 },
      { "productId": 3, "quantity": 1 }
    ]
  }'
```

O valor total do pedido é calculado automaticamente com base no preço e na quantidade de cada item. Caso o usuário ou algum produto não seja encontrado, ou a lista de itens esteja vazia, a API retorna um erro de negócio (`CreateOrderException`).

## Dados iniciais (seed)

O arquivo `data.sql` insere automaticamente, na primeira execução:

- **Produtos**: Computer, Smartphone, Mouse
- **Tags**: Eletronics, Home, Apple
- Vínculos entre produtos e tags

## Licença

Projeto de estudo/portfólio — sem licença definida.

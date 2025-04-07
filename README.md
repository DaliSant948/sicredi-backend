# 🗳️ Desafio Técnico - Sicredi (Backend)

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Kafka](https://img.shields.io/badge/Kafka-3.6.0-black)
![Docker](https://img.shields.io/badge/Docker-ready-blue)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

## 📚 Sobre o Projeto

API para gerenciamento de pautas, sessões de votação e votos de associados. Ao final de cada sessão de votação, um evento é publicado no Kafka com os dados da sessão encerrada. O sistema permite também a consulta ao resultado da votação via endpoint REST.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Docker e Docker Compose
- Swagger (OpenAPI)
- JUnit e Mockito
- Kafdrop (monitoramento do Kafka)

---

## ▶️ Como Rodar o Projeto

### Pré-requisitos:
- Java 21
- Maven
- Docker + Docker Compose

### Passos:

1. Subir containers do Kafka, Zookeeper, PostgreSQL e Kafdrop:

```bash
docker-compose up -d
```

2. Rodar a aplicação localmente:

```bash
./mvnw spring-boot:run
```

---

## 🔗 Endpoints Principais

### 📌 Pautas
- Criar pauta: `POST /api/pautas`
- Listar pautas: `GET /api/pautas`

### 📌 Sessões de Votação
- Abrir sessão: `POST /api/sessoes`

### 📌 Votos
- Votar: `POST /api/votos/{pautaId}`
- Obter resultado da votação: `GET /api/votos/resultado/{pautaId}`

### 🧪 Exemplo de Payloads

#### Criar pauta:
```json
{
  "descricao": "Pauta sobre orçamento anual"
}
```

#### Abrir sessão:
```json
{
  "pautaId": 1,
  "duracaoEmMinutos": 5
}
```

#### Votar:
```json
{
  "cpfAssociado": "12345678900",
  "opcao": "SIM"
}
```

#### Exemplo de resposta de resultado:
```json
{
  "pautaId": 1,
  "totalVotosSim": 3,
  "totalVotosNao": 1,
  "resultado": "APROVADA"
}
```

---

## 📖 Swagger UI
Acesse a documentação interativa em:
🔗 http://localhost:8081/swagger-ui/index.html

---

## 📊 Kafka
- Tópico de sessões encerradas: `sessoes-encerradas`
- Tópico de votos realizados: `votacoes-realizadas`
- Acompanhe pelo Kafdrop:
  🔗 http://localhost:9000

---

## 🧪 Testes
Para rodar os testes unitários:
```bash
./mvnw test
```
Testes cobrem:
- Camada de serviço
- Camada de controller
- Regras de negócio
- Exceções

---

## 🗂️ Estrutura de Pastas

```text
src/
├── main/
│   ├── java/com/sicredi/desafio_sicredi/
│   │   ├── config/                 # Configurações Kafka e Swagger
│   │   ├── controller/             # Endpoints REST
│   │   ├── dto/                    # Objetos de transferência de dados
│   │   ├── exception/              # Exceções customizadas
│   │   ├── model/                  # Entidades JPA
│   │   ├── repository/             # Repositórios JPA
│   │   ├── service/                # Lógica de negócio e scheduler
│   └── resources/
│       └── application.properties  # Configurações da aplicação
├── test/                           # Testes unitários e mocks
```

---

## 📥 Collection Postman

[Download da Collection Postman](postman_collection_sicredi.json)

---

## ✍️ Autora
Dalila 🇧🇷  
💻 Backend Developer | ☕ Coffee Lover


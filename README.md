# 🗳️ Desafio Técnico - Sicredi (Backend)

## 📚 Sobre o projeto

API para gerenciamento de pautas, sessões de votação e votos de associados, com publicação de eventos via Apache Kafka ao final de cada sessão.  
Desenvolvido como parte do desafio técnico proposto pela Sicredi.

---

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Docker + Docker Compose
- Swagger (springdoc-openapi)
- JUnit + Mockito
- Kafdrop (monitoramento do Kafka)

---

## 🧪 Como rodar o projeto

### ✅ Pré-requisitos:

- Docker + Docker Compose
- Java 21
- Maven (ou `./mvnw`)

---

### ▶️ Passo a passo:

1. Subir os containers (PostgreSQL, Kafka, Zookeeper, Kafdrop):

```bash
docker-compose up -d


Rodar a aplicação localmente:
./mvnw spring-boot:run


🛰️ Monitoramento Kafka
Acesse o Kafdrop:
🔗 http://localhost:9000

Tópicos utilizados:
sessoes-encerradas
votacoes-realizadas


📖 Swagger
Documentação interativa da API disponível em:
🔗 http://localhost:8081/swagger-ui/index.html


🔗 Endpoints principais
Criar pauta: POST /api/pautas
Listar pautas: GET /api/pautas
Abrir sessão: POST /api/sessoes
Votar: POST /api/votos/{pautaId}

Exemplo de body voto:

{
  "cpfAssociado": "12345678900",
  "opcao": "SIM"
}


✅ Testes
Para rodar os testes unitários:
./mvnw test

Todos os testes cobrem:
Camada de serviço (SessaoVotacaoService, VotoService)
Camada de controller (SessaoVotacaoController, VotoController)
Validações e exceções


📦 Kafka - Resultado publicado
Mensagem enviada no Kafka ao encerrar sessão:
📢 Sessão encerrada para a pauta ID: 1


## 📊 Cobertura de testes
Relatório gerado com **JaCoCo**:  
📎 [🔗 Ver cobertura online](https://dalisant948.github.io/sicredi-backend/)



✍️ Autora
Dalila 🇧🇷
💻 Backend Developer | ☕ Coffee lover 
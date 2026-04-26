# 💰 Budget System - Sistema de Orçamentos

Sistema web para criação e gerenciamento de orçamentos profissionais, com geração de PDF.

---

## Sobre o projeto

O Budget System é uma aplicação fullstack desenvolvida com foco em prática real de mercado, permitindo:

- Cadastro de clientes
- Criação de orçamentos com múltiplos materiais
- Cálculo automático de valores
- Geração de PDF profissional
- Interface web simples e funcional

---

## Tecnologias utilizadas

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

### Frontend
- HTML
- CSS
- JavaScript 

### Outros
- iText PDF (geração de PDF)

---

## Funcionalidades

### 👤 Clientes
- Criar cliente
- Listar clientes
- Deletar cliente

### 📄 Orçamentos
- Criar orçamento
- Listar orçamentos
- Deletar orçamento
- Calcular total automaticamente

### 🧾 PDF
- Gerar orçamento em PDF
- Layout profissional
- Tabela de materiais
- Total destacado

---

## 🖥️ Como rodar o projeto

### 🔧 Pré-requisitos

- Java 21
- Maven
- PostgreSQL

---

### 📦 Backend

```bash
# clonar o projeto
git clone https://github.com/seu-usuario/budget-system.git

# entrar na pasta
cd budget-system

# rodar
mvn spring-boot:run
```

### Banco de dados

- Configure no application.yaml:
````
spring:
  application:
    name: budget-system

  datasource:
    url: jdbc:postgresql://localhost:5432/budget_db
    username: postgres
    password: root

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
````

### Frontend

# Abra o arquivo:
resources/static/index.html

### Estrutura do projeto

````
budget-system
│
├── backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│
├── frontend
│   ├── index.html
│   ├── customers.html
│   ├── create-budget.html
│   ├── budgets.html
````
### Prints

<img width="720" height="628" alt="image" src="https://github.com/user-attachments/assets/c2d587c8-0d9d-42ea-af88-a74422aabd57" />

<img width="1126" height="929" alt="image" src="https://github.com/user-attachments/assets/1ded1d63-d600-495f-b3fb-95724b4a01c7" />

<img width="1237" height="429" alt="image" src="https://github.com/user-attachments/assets/199f137e-3c69-40a2-b8d6-0d2d86a4670a" />

<img width="1281" height="968" alt="image" src="https://github.com/user-attachments/assets/8e2ac2bd-9c43-4e6a-8c6e-4146f04593b6" />

<img width="988" height="943" alt="image" src="https://github.com/user-attachments/assets/29fdbd74-462e-447a-a2d3-a24be10a8446" />

### Melhorias futuras

- Autenticação de usuários
- Deploy na nuvem

### Aprendizados

Durante o desenvolvimento deste projeto, foram trabalhados conceitos como:

- Modelagem de dados
- Relacionamentos JPA
- Integração frontend e backend
- Manipulação de arquivos (PDF)
- Organização de projeto
--- 

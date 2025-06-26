# API de Autenticação e Autorização JWT
- API RESTful com autenticação JWT, segurança, monitoramento e testes automatizados usando Spring Boot 3.x.

# Tecnologias
- Spring Boot 3.x
- Spring Security com JWT
- Spring Data JPA
- H2 Database
- Swagger/OpenAPI
- Spring Boot Actuator
- Prometheus & Grafana
- Docker & Docker Compose
- JUnit 5 & Mockito
- Funcionalidades
- Autenticação e registro de usuários
- Autorização baseada em roles (ROLE_USER, ROLE_ADMIN)
- Endpoints CRUD protegidos
- Documentação automática da API
- Monitoramento em tempo real              

# Instalação e Execução
- Pré-requisitos
Java 17+
Maven
Docker & Docker Compose (para monitoramento)

# Railway
- URL do projeto hospedado <br>
`https://railway.com/project/b108bac1-df68-4054-9a3a-9a8614d63532?environmentId=0e2b4096-0752-439d-ae51-a497f9fc1f00`

# Endpoints Principais
- POST /auth/register - Registro de usuários
- POST /auth/login - Autenticação e geração de token
- GET /api/users/me - Perfil do usuário autenticado
- GET /api/products - Listar produtos
- POST /api/products - Criar produto (apenas ADMIN)

# Documentação
Acesse a documentação Swagger em: 
- http://localhost:8080/swagger-ui.html

# Monitoramento
Métricas: http://localhost:8080/actuator/prometheus
Grafana: http://localhost:3000 (admin/admin)

# Executar testes unitários
`mvn test`

# Testes de carga com JMeter
- Importar o arquivo AuthAPILoadTest.jmx no JMeter

# Docker
- Construir imagem
`docker build -t auth-api .`

- Executar container
`docker run -p 8080:8080 auth-api`

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

# Estrutura do Projeto
src/
├── main/
│   ├── java/com/auth/api/
│   │   ├── config/        # Configurações (Security, OpenAPI, Actuator)
│   │   ├── controller/    # Controladores REST
│   │   ├── dto/           # Objetos de transferência de dados
│   │   ├── entity/        # Entidades JPA
│   │   ├── exception/     # Tratamento de exceções
│   │   ├── repository/    # Repositórios JPA
│   │   ├── security/      # Configuração JWT e segurança
│   │   └── service/       # Lógica de negócios
│   └── resources/
│       └── application.yml
└── test/                  # Testes unitários

# Instalação e Execução
- Pré-requisitos
Java 17+
Maven
Docker & Docker Compose (para monitoramento)

# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run

# Iniciar Prometheus e Grafana
docker-compose up -d

# Endpoints Principais
- POST /auth/register - Registro de usuários
- POST /auth/login - Autenticação e geração de token
- GET /api/users/me - Perfil do usuário autenticado
- GET /api/products - Listar produtos
- POST /api/products - Criar produto (apenas ADMIN)

# Documentação
Acesse a documentação Swagger em: http://localhost:8080/swagger-ui.html

# Monitoramento
Métricas: http://localhost:8080/actuator/prometheus
Grafana: http://localhost:3000 (admin/admin)

# Executar testes unitários
mvn test

# Testes de carga com JMeter
- Importar o arquivo AuthAPILoadTest.jmx no JMeter

# Docker
- Construir imagem
docker build -t auth-api .

- Executar container
docker run -p 8080:8080 auth-api
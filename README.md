# API-Java-Spring-Pacientes
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

# POC SobreVidas - API de Gestão de Pacientes

Esta é uma API REST desenvolvida em **Spring Boot 3.4** para o gerenciamento de pacientes. O projeto faz parte de uma Prova de Conceito (POC) para o sistema SobreVidas, focando em robustez, validação de dados e facilidade de integração.

## Tecnologias

- **Java 21** (LTS)
- **Spring Boot 3.4.0**
- **Spring Data JPA**
- **PostgreSQL** (Produção/Dev)
- **H2 Database** (Testes)
- **MapStruct** (Mapeamento de DTOs)
- **Lombok** (Produtividade)
- **SpringDoc OpenAPI 3** (Swagger)
- **JUnit 5 & Mockito** (Testes Unitários e de Integração)
- **Docker 29.3.0**
---
## Organização do projeto (diretórios)

    ./
    ├── demo/ 
    │    ├── src/
    │         ├── main/
    │         │   ├── java/
    │         │   │   └── com/example/pocsobrevidas/
    │         │   │       ├── controller/  # endpoints da API REST e documentação Swagger
    │         │   │       ├── model/      # entidades de mapeamento objeto-relacional (JPA)
    │         │   │       ├── repository/  # persistência de dados e queries customizadas
    │         │   │       ├── request/    # Data Transfer Objects (DTOs) de entrada e mapper
    │         │   │       ├── service/     # regras de negócio e lógica de importação de CSV
    │         │   │       └── APIPocSobrevidasApplication.java # classe inicial do Spring Boot
    │         │   └── resources/           # application.properties (configurações do Spring)
    │         │            └── csv/        #csv com dados falsos de pacientes
    │         └── test/                     
    │             └── java/
    │                 └── com/example/pocsobrevidas/
    │                     ├── controller/  # testes unitários dos endpoints e validação de respostas HTTP
    │                     ├── repository/  # testes de persistência e consultas à base de dados em memória (H2)
    │                     └── service/     # testes das regras de negócio isolados com Mockito
    ├── docker-compose.yml           # configuração dos serviços Docker (PostgreSQL)
    └── pom.xml                      # arquivo de gerenciamento de dependências do Maven
---
## Como executar

### Pré-requisitos
- JDK 21 instalado.
- Maven 3.9.12  (ou use o `./mvnw` incluso).
- Docker 29.3.0
---

### Passo a passo
**1. Clone o repositório:**
   ```bash
   git clone https://github.com/Tiagosalesr/API-Java-Spring-Pacientes.git
  ```

---

**2. Subindo o Banco de Dados**
   Certifique-se de ter o Docker instalado e execute o comando abaixo na raiz do projeto para subir a instância do banco
   ```bash
   docker-compose up -d
   ```

**Nota:** O arquivo docker-compose.yml já está configurado para expor a porta 5433 e persistir os dados em um volume local.
(O banco rodará na porta 5433, com o usuário tiago, senha 123 e database api_poc_sobrevidas_db, exatamente como o Spring Boot espera no arquivo application.properties).

---
**Guia de Comandos Docker Úteis**

Para todos os comandos abaixo, certifique-se de estar no diretório **raiz** do projeto (onde o arquivo `docker-compose.yml` está localizado).

* **Parar todos os contêineres definidos no projeto:**
```bash
docker compose stop
````
* **Parar e remover todos os contêineres e redes:**
```bash
docker compose down
```
* **Parar e remover todos os contêineres, redes e volumes associados (Atenção: isto apagará os dados do banco):**

```bash
docker compose down -v
```

* **Verificar os contêineres em execução:**
```bash
docker ps
```
* **Verificar logs do contêiner do banco de dados, seguindo em tempo real:**
```bash
docker logs -f api_poc_sobrevidas_db
```
* **Entrar no banco de dados PostgreSQL rodando no Docker:**
```bash
docker exec -it postgres_poc psql -U tiago -d api_poc_sobrevidas_db
```
---

**3. Executando a Aplicação SpringBoot**

Com o banco de dados rodando via Docker, você pode iniciar a aplicação de duas formas:

Via IDE: Basta executar a classe principal APIPocSobrevidasApplication.java diretamente na sua IDE.

Via Terminal (Maven): Na raiz do projeto, execute o comando:
```bash
mvn spring-boot:run
```
(A aplicação estará inicializada e disponível na porta 8080)

---

### Ferramentas de Gerenciamento
Para visualizar os dados salvos no banco Docker, você pode usar:

DBeaver ou pgAdmin: Conecte usando localhost:5433, e usuário e senha presentes no application.properties.

IntelliJ Database Tools: A aba "Database" no canto direito do IntelliJ também permite gerenciar o banco diretamente pela IDE.

---

### Ajustes de Schema (SQL)
Execute os seguintes comandos SQL no console do seu gerenciador de banco para garantir a consistência das regras de negócio:
```PostgreSQL
-- Configura o ID como Auto-Incremento (Identity)
ALTER TABLE pacientes ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY;

-- Ajusta o tamanho do CPF (Padrão 11 dígitos)
ALTER TABLE pacientes ALTER COLUMN cpf TYPE VARCHAR(11);

-- Aplica restrições de obrigatoriedade (NOT NULL)
ALTER TABLE pacientes ALTER COLUMN nome SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN cpf SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN data_nascimento SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN telefone_celular SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN nome_mae SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN sexo SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN participa_smart_monitor SET NOT NULL;
```
**Por que esses ajustes são importantes?**

- Identity: Garante que o banco gerencie os IDs automaticamente sem conflitos.

- Not Null: Evita que o sistema salve registros incompletos, prevenindo erros como o PropertyValueException que mapeamos durante os testes.

- Varchar(11): Otimiza o armazenamento do CPF, removendo espaços desnecessários.
---
### Documentação da API
A aplicação utiliza o SpringDoc OpenAPI (Swagger) para documentação e interface de testes interativa.

Com a aplicação em execução, acesse a documentação através do navegador:

Swagger UI: http://localhost:8080/swagger-ui/index.html
Aqui é onde reside os detalhes dos contratos de requisição e resposta para a API.

---
### População Inicial fictícia do Banco de dados.
Para fins de teste da aplicação, existe um arquivo pacientes.csv em demo/src/main/resources/csv contendo uma carga inicial de dados reais/fictícios.

Como popular o banco:

1. Com a aplicação rodando, acesse o Swagger UI: http://localhost:8080/swagger-ui/index.html
2. Expanda o endpoint POST /pacientes/importar.
3. Clique no botão "Try it out".
4. Clique em Execute, o endpoint busca o arquivo em demo/src/main/resources/csv e importa seus dados.

Finalizado! Seu banco agora está populado para testes.

---
## Testes

O projeto possui uma suíte de testes automatizados cobrindo as camadas de Controller, Service e Repository.

Para rodar todos os testes e ver o relatório de sucesso, certifique-se de estar na pasta /demo e rode:
```bash
./mvnw test
```

Para rodar com relatório de cobertura (Coverage) no IntelliJ:
- Botão direito na pasta src/test/java -> Run 'All Tests' with Coverage







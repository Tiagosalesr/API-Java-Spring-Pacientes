
*25/03/2026*

- Implementação do Endpoint listAll: Criação de método GET no PacienteController para retorno da lista completa de registros via service.findAll().

- Implementação do Endpoint findByCpf: Adição de busca por @PathVariable no controller, permitindo a recuperação de pacientes específicos através do documento.

- Criação de Query Method no Repository: Definição da assinatura findByCpf(String cpf) no PacienteRepository para automação da consulta SQL via Spring Data JPA.

- Tratamento de Retornos HTTP: Configuração de respostas com ResponseEntity e Optional, estabelecendo os status 200 OK para buscas bem-sucedidas e 404 Not Found para CPFs inexistentes.

*24/03/2026*

- Desenvolvimento de Lógica de Serviço: Implementação do método de importação de arquivos CSV no PacienteService, utilizando BufferedReader e ClassPathResource para processamento eficiente de dados.

- Tratamento e Sanitização de Dados: Aplicação de lógica de limpeza de strings (remoção de aspas e tratamento de valores "NULL") e conversão de tipos complexos como LocalDateTime e Boolean.

- Refatoração de Infraestrutura de Banco: Reestruturação da chave primária da tabela para o padrão IDENTITY e sincronização do mapeamento JPA para garantir a geração automática de IDs pelo PostgreSQL.

- Exposição de Endpoints REST: Criação do PacienteController para disponibilizar gatilhos de importação via requisições HTTP GET, permitindo a interação entre o navegador e a camada de serviço.

*23/03/2026*
Estou trabalhando no projeto a 4 dias, mas decidi hoje criar o diário. 
Vou separar o dia de hoje em duas parte: Tarefas dos dias passados e Tarefas feitas hoje.

*Tarefas dos dias passados(19/03-22/03):*

- Versionamento: Inicialização do repositório local e publicação do remote no GitHub.
- Infraestrutura: Modelagem e execução do arquivo docker-compose.yml para orquestração do container PostgreSQL.
- Bootstrap do Projeto: Estruturação da aplicação utilizando Spring Boot 3.4 com gerenciamento de dependências via Maven.
- Ingestão de Dados: Carga inicial dos dados (Seed) a partir do arquivo CSV para o banco de dados via Client SQL.

*Tarefas realizadas hoje:*

- Mapeamento Objeto-Relacional: Implementação da Entity Paciente utilizando anotações do JPA e Lombok para otimização de código.
- Evolução de Esquema: Aplicação de migrações estruturais no banco de dados via script para garantir a integridade da tabela de pacientes.

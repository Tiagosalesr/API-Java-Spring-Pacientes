
*05/04/2026*
- Cobertura de Testes Unitários (PacienteService): Implementação de testes automatizados para os métodos addPacient, findByCpf, updateById e deleteById, validando o comportamento do sistema tanto em cenários de sucesso quanto em disparos de exceção.

- Validação de Comportamento e Mocks: Uso do Mockito.verify e ArgumentMatchers para assegurar que as interações com o PacienteRepository ocorrem conforme o esperado, impedindo, por exemplo, que um registro seja salvo caso a validação de CPF falhe.

- Cobertura de Testes Unitários (PacienteController): Configuração do MockMvc para validação de endpoints do PacienteController, assegurando o retorno de status HTTP corretos (200 OK, 201 Created, 204 No Content) e a integridade do corpo das respostas JSON.

- Persistência e Validação de Repositório (PacienteRepository): Execução de testes com @DataJpaTest para validar consultas customizadas (findByCpf) e o ciclo de vida das entidades, assegurando que as restrições de integridade do banco.

*31/03/2026*

- Implementação do Endpoint save (POST): Desenvolvimento de método para persistência de novos registros, utilizando o status HTTP 201 Created para confirmar a criação do recurso no servidor.

- Validação de Regra de Negócio (CPF Único): Uso do método ifPresent do Optional no Service para disparar uma ResponseStatusException (409 Conflict), impedindo duplicidade de registros e garantindo a consistência do banco.

- Mapeamento de Entrada com MapStruct: Conversão automática do DTO de entrada para a Entidade, assegurando que o fluxo de criação seja isolado e livre de manipulações indevidas de campos protegidos.

- Integração do SpringDoc OpenAPI 3 (Swagger): Adição da dependência e configuração de classe @Configuration para geração de documentação interativa e testes de endpoints via interface gráfica.


*30/03/2026*

- Implementação do Endpoint updateById (PUT): Desenvolvimento de método para atualização integral de registros, recebendo ID via URL e dados novos via @RequestBody.

- Integração com MapStruct e @MappingTarget: Configuração de interface Mapper para realizar a "cópia" de dados do Request para a Entidade existente no banco, evitando a criação de objetos órfãos e preservando a integridade dos dados.

- Lógica de Atualização Segura no Service: Uso do método .map() sobre o Optional para garantir que a atualização e o repository.save() ocorram apenas em registros previamente localizados.

- Blindagem de Identidade no Update: Implementação de trava lógica no Service (pacienteBanco.setId(id)) para assegurar que o registro atualizado seja sempre o referente ao ID da URL, ignorando possíveis inconsistências no corpo do JSON.

*27/03/2026*

- Implementação do Endpoint deleteById: Criação de método DELETE no PacienteController utilizando @PathVariable para exclusão lógica e física de registros.

- Padronização de Exceções de Deleção: Configuração de ResponseStatusException (404 Not Found) com mensagens personalizadas e logs de aviso (log.warn) para tentativas de exclusão de IDs inexistentes.


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

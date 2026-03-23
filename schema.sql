
TRUNCATE TABLE pacientes;

ALTER TABLE pacientes ALTER COLUMN cpf TYPE VARCHAR(11);

UPDATE pacientes
SET
    cep = NULLIF(NULLIF(cep, 'NULL'), ''),
    num_cartao_sus = NULLIF(NULLIF(num_cartao_sus, 'NULL'), ''),
    telefone_responsavel = NULLIF(NULLIF(telefone_responsavel, 'NULL'), ''),
    complemento = NULLIF(NULLIF(complemento, 'NULL'), ''),
    email = NULLIF(NULLIF(email, 'NULL'), ''),
    endereco = NULLIF(NULLIF(endereco, 'NULL'), ''),
    estado = NULLIF(NULLIF(estado, 'NULL'), ''),
    cidade = NULLIF(NULLIF(cidade, 'NULL'), ''),
    num_endereco = NULLIF(NULLIF(num_endereco, 'NULL'), ''),
    bairro = NULLIF(NULLIF(bairro, 'NULL'), ''),
    eh_etilista = NULLIF(NULLIF(eh_etilista, 'NULL'), ''),
    eh_tabagista = NULLIF(NULLIF(eh_tabagista, 'NULL'), ''),
    tem_lesao_suspeita = NULLIF(NULLIF(tem_lesao_suspeita, 'NULL'), '')
WHERE TRUE; -- O WHERE TRUE garante que o comando rode em todas as linhas


ALTER TABLE pacientes
ALTER COLUMN eh_etilista TYPE BOOLEAN
        USING (LOWER(eh_etilista::text)::boolean);

ALTER TABLE pacientes
ALTER COLUMN eh_tabagista TYPE BOOLEAN
        USING (LOWER(eh_tabagista::text)::boolean);

ALTER TABLE pacientes
ALTER COLUMN tem_lesao_suspeita TYPE BOOLEAN
        USING (LOWER(tem_lesao_suspeita::text)::boolean);


ALTER TABLE pacientes ALTER COLUMN nome SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN cpf SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN data_nascimento SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN telefone_celular SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN nome_mae SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN sexo SET NOT NULL;
ALTER TABLE pacientes ALTER COLUMN participa_smart_monitor SET NOT NULL;
--Projeto N2 

CREATE DATABASE "db_projetoN2"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE laboratorios
(
id_laboratorio serial NOT NULL,
numero integer,
descricao character varying(80),
constraint "PK_laboratorios_id_laboratorio" PRIMARY KEY (id_laboratorio)
);

CREATE TABLE computadores
(
id_computador serial NOT NULL,
numero integer,
descricao character varying(80),
constraint "PK_computadores_id_computador" PRIMARY KEY (id_computador)
);

CREATE TABLE tipos_solicitacao
(
id_tipo serial NOT NULL,
nome character varying(80),
descricao character varying(80),
constraint "PK_tipos_solicitacao_id_tipo" PRIMARY KEY (id_tipo)
);

CREATE TABLE usuarios
(
id_usuario serial NOT NULL,
login character varying(100),
senha character varying(8),
role character varying(15),
cpf character varying(14),
constraint "PK_usuarios_id_usuario" PRIMARY KEY (id_usuario)
);

CREATE TABLE alunos
(
id_aluno serial NOT NULL, 
nome character varying(100),
matricula character varying(10),
cpf character varying(14),
data_nascimento date,
email character varying(100),
constraint "PK_alunos_id_aluno" PRIMARY KEY (id_aluno)
);

CREATE TABLE solicitacoes
(
id_solicitacao serial NOT NULL,
descricao character varying(400),
data_solicitacao date,
id_aluno integer,
id_laboratorio integer,
id_computador integer,
id_tipo integer,
constraint "PK_solicitacoes_id_solicitacao" PRIMARY KEY (id_solicitacao),
constraint "FK_solicitacoes_id_aluno" FOREIGN KEY (id_aluno) REFERENCES alunos (id_aluno),
constraint "FK_solicitacoes_id_laboratorio" FOREIGN KEY (id_laboratorio) REFERENCES laboratorios (id_laboratorio),
constraint "FK_solicitacoes_id_computador" FOREIGN KEY (id_computador) REFERENCES computadores (id_computador),
constraint "FK_solicitacoes_id_tipo" FOREIGN KEY (id_tipo) REFERENCES tipos_solicitacao (id_tipo)
);

CREATE TABLE status_retorno
(
id_status serial NOT NULL,
descricao character varying(13),
constraint "PK_status_retorno_id_status" PRIMARY KEY (id_status)
);

CREATE TABLE funcionarios
(
id_funcionario serial NOT NULL,
nome character varying(100),
cpf character varying(14),
data_nascimento date,
email character varying(100),
constraint "PK_funcionarios_id_funcionario" PRIMARY KEY (id_funcionario)
);

CREATE TABLE retorno_solicitacao
(
id_retorno serial NOT NULL,
descricao character varying(200),
data_retorno date,
id_solicitacao integer,
id_status integer,
id_funcionario integer,
constraint "PK_retorno_solicitacao_id_retorno" PRIMARY KEY (id_retorno),
constraint "FK_retorno_solicitacao_id_solicitacao" FOREIGN KEY (id_solicitacao) REFERENCES solicitacoes (id_solicitacao),
constraint "FK_retorno_solicitacao_id_status" FOREIGN KEY (id_status) REFERENCES status_retorno (id_status),
constraint "FK_retorno_solicitacao_id_funcionario" FOREIGN KEY (id_funcionario) REFERENCES funcionarios (id_funcionario)
);

INSERT INTO public.usuarios(
	login, senha, role, cpf)
	VALUES ('admin', 'admin', 'administrador', '00000000000');
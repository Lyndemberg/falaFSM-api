﻿CREATE TABLE ADMINISTRADOR( 
	Email VARCHAR(45),
	Nome VARCHAR(45) NOT NULL,
	Login VARCHAR(15) NOT NULL UNIQUE,
	Senha VARCHAR(60) NOT NULL,
	CONSTRAINT PK_Admin_Email PRIMARY KEY(Email),
	CONSTRAINT Verifica_Admin_Email CHECK (Email LIKE '%@%')
);

CREATE TABLE ALUNO(
	Matricula VARCHAR(45),
	Email VARCHAR(45) NOT NULL UNIQUE,
	Nome VARCHAR(45) NOT NULL,
	Login VARCHAR(15) NOT NULL UNIQUE,
	Senha VARCHAR(60) NOT NULL,
	NomeCurso VARCHAR(45) NOT NULL,
	CONSTRAINT PK_Aluno_Matricula PRIMARY KEY(Matricula),
	CONSTRAINT Verifica_Aluno_Email CHECK (Email LIKE '%@%')
);

CREATE TABLE ENQUETE(
	idEnquete SERIAL,
	Nome VARCHAR(50) NOT NULL UNIQUE,
	Descricao VARCHAR(500),
	Foto VARCHAR,
	emailAdmin VARCHAR(45) NOT NULL,
	CONSTRAINT PK_Enquete_Id PRIMARY KEY(IdEnquete),
	CONSTRAINT FK_Admin_Email FOREIGN KEY(emailAdmin) REFERENCES Administrador(email) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE OPCOES(
	idEnquete INTEGER NOT NULL, 
	Opcao VARCHAR NOT NULL,
	CONSTRAINT PK_Opcao_IdEnquete_Opcao PRIMARY KEY(IdEnquete, Opcao),
	CONSTRAINT Combinacao_EnqueteOpcao_Unica UNIQUE(idEnquete, Opcao),
	CONSTRAINT FK_Enquete_Id FOREIGN KEY(idEnquete) REFERENCES ENQUETE(idEnquete) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE CURSO(
	Nome VARCHAR(45),
	Descricao VARCHAR NOT NULL,
	CONSTRAINT PK_Curso_Nome PRIMARY KEY(Nome)
);

CREATE TABLE SETOR(
	Nome VARCHAR(45),
	CONSTRAINT PK_Setor_Nome PRIMARY KEY(Nome)
);

ALTER TABLE Aluno ADD CONSTRAINT FK_Curso_Nome FOREIGN KEY(NomeCurso) REFERENCES CURSO(Nome) ON DELETE RESTRICT ON UPDATE CASCADE;

CREATE TABLE COMENTAENQUETE(
	matriculaAluno VARCHAR(45),
	idEnquete INTEGER,
	Comentario VARCHAR(500) NOT NULL,
	CONSTRAINT PK_Comentario_MatAluno_IdEnquete PRIMARY KEY(matriculaAluno, idEnquete),
	CONSTRAINT FK_Aluno_Matricula FOREIGN KEY(matriculaAluno) REFERENCES ALUNO(Matricula) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_Enquete_Id FOREIGN KEY(idEnquete) REFERENCES ENQUETE(idEnquete) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE RESPONDEENQUETE(
	matriculaAluno VARCHAR(45),
	idEnquete INTEGER,
	Resposta VARCHAR,
	CONSTRAINT PK_Resposta_MatAluno_IdEnquete PRIMARY KEY(matriculaAluno, idEnquete),
	CONSTRAINT FK_Aluno_Matricula FOREIGN KEY(matriculaAluno) REFERENCES ALUNO(Matricula) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_Enquete_Id FOREIGN KEY(idEnquete) REFERENCES ENQUETE(idEnquete) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE ENQUETESCURSO(
	idEnquete INTEGER,
	nomeCurso VARCHAR(45),
	CONSTRAINT PK_IdEnquete_NomeCurso PRIMARY KEY(idEnquete, nomeCurso),
	CONSTRAINT FK_Enquete_Id FOREIGN KEY(idEnquete) REFERENCES ENQUETE(idEnquete) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_Curso_Nome FOREIGN KEY(nomeCurso) REFERENCES CURSO(Nome) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE ENQUETESSETOR(
	idEnquete INTEGER,
	nomeSetor VARCHAR(45),
	CONSTRAINT PK_IdEnquete_NomeSetor PRIMARY KEY(idEnquete, nomeSetor),
	CONSTRAINT FK_Enquete_Id FOREIGN KEY(idEnquete) REFERENCES ENQUETE(idEnquete) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT FK_Setor_Nome FOREIGN KEY(nomeSetor) REFERENCES SETOR(Nome) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE INDEX Administrador_Email ON ADMINISTRADOR(Email);
CREATE INDEX Matricula_Aluno ON ALUNO(Matricula);
CREATE INDEX Id_Enquete ON ENQUETE(idEnquete);
CREATE INDEX Id_Opcao_idEnquete ON OPCOES(IdEnquete);
CREATE INDEX Id_Opcao_Opcao ON OPCOES(Opcao);
CREATE INDEX Nome_Curso ON CURSO(Nome);
CREATE INDEX Nome_Setor ON SETOR(Nome);
CREATE INDEX MatriculaAluno_ComentaEnquete ON COMENTAENQUETE(matriculaAluno);
CREATE INDEX IdEnquete_ComentaEnquete ON COMENTAENQUETE(idEnquete);
CREATE INDEX IdEnquete_RespondeEnquete ON RESPONDEENQUETE(IdEnquete);
CREATE INDEX IdEnquete_EnquetesCurso ON ENQUETESCURSO(IdEnquete);
CREATE INDEX NomeCurso_EnquetesCurso ON ENQUETESCURSO(NomeCurso);
CREATE INDEX IdEnquete_EnquetesSetor ON ENQUETESSETOR(IdEnquete);
CREATE INDEX NomeSetor_EnquetesSetor ON ENQUETESSETOR(NomeSetor);
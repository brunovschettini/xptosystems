-- Folha de respostas
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019
-- Parte 3 – PL/SQL Prático
-- Local do teste: https://livesql.oracle.com

/*
Exercício 1.
Crie as seguintes tabelas, em um script SQL com o nome de SCRIPT1.SQL:

EXAME_NF
IDNF	NUMERO
NUMERO	NUMERO
DATACADASTRO	DATA
TOTALGERAL	NUMERO

EXAME_ITEMNF
IDITEMNF	NUMERO
IDNF	NUMERO
IDPRODUTO	NUMERO
QTDE	NUMERO
VALOR	NUMERO

*/

-- PRODUTOS
-- DROP TABLE PRODUTO;
create table PRODUTO (
   IDPRODUTO number not null constraint produto_pk primary key,
   CODIGOINTERNO varchar2(60) not null,
   DESCR varchar2(120) not null,
   ATIVO char(1) DEFAULT 'S' not null
); 


-- DROP TABLE EMBALAGEM;

create table EMBALAGEM (
   IDPRODUTO number not null,
   BARRA varchar2(32) not null,
   DESCR varchar2(80) not null,
   FATORCONVERSAO number not null,
   ALTURA number DEFAULT 0,
   LARGURA number DEFAULT 0,
   COMPRIMENTO number,
   ATIVO char(1) DEFAULT 'S' not null,
   CONSTRAINT fk_produto
   FOREIGN KEY (IDPRODUTO)
   REFERENCES PRODUTO(IDPRODUTO)

);

ALTER TABLE EMBALAGEM ADD CONSTRAINT embalagem_pk PRIMARY KEY (IDPRODUTO, BARRA);

 


-- drop table EXAME_ITEMNF; 
-- drop table EXAME_NF;

-- Parte 3 – PL/SQL Prático
-- Exercício 1.

create table EXAME_NF (
    IDNF number not null constraint EXAME_NF_PK primary key,
    NUMERO number DEFAULT 0 not null,
    DATACADASTRO DATE not null,
    TOTALGERAL number DEFAULT 0 not null
);


-- select * from EXAME_NF

CREATE SEQUENCE EXAME_NF_seq START WITH 1;

CREATE OR REPLACE TRIGGER EXAME_NF_bir BEFORE INSERT ON EXAME_NF FOR EACH ROW
BEGIN
  SELECT EXAME_NF_seq.NEXTVAL
  INTO   :new.IDNF
  FROM   dual;
END;
/


create table EXAME_ITEMNF (
    IDITEMNF number not null constraint EXAME_ITEMNF_PK primary key,
    IDNF number not null,
    IDPRODUTO number not null,
    NUMERO number DEFAULT 0 not null,
    QTDE number DEFAULT 0 not null,
    VALOR number DEFAULT 0 not null,
    CONSTRAINT fk_EXAME_ITEMNF_IDITEMNF
        FOREIGN KEY (IDNF)
            REFERENCES EXAME_NF(IDNF)
    -- CONSTRAINT fk_EXAME_ITEMNF_IDPRODUTO
    --    FOREIGN KEY (IDPRODUTO)
    --            REFERENCES PRODUTO(IDPRODUTO)                    

);

-- select * from EXAME_ITEMNF

CREATE SEQUENCE EXAME_ITEMNF_seq START WITH 1;

CREATE OR REPLACE TRIGGER EXAME_ITEMNF_bir BEFORE INSERT ON EXAME_ITEMNF FOR EACH ROW
BEGIN
  SELECT EXAME_ITEMNF_seq.NEXTVAL
  INTO   :new.IDITEMNF
  FROM   dual;
END;
/
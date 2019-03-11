-- Folha de respostas
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019

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


create table EXAME_ITEMNF (
    IDITEMNF number not null constraint EXAME_ITEMNF_PK primary key,
    IDNF number not null,
    IDPRODUTO number not null,
    NUMERO number DEFAULT 0 not null,
    QTDE number DEFAULT 0 not null,
    VALOR number DEFAULT 0 not null,
    CONSTRAINT fk_EXAME_ITEMNF_IDITEMNF
        FOREIGN KEY (IDNF)
            REFERENCES EXAME_NF(IDNF),
    CONSTRAINT fk_EXAME_ITEMNF_IDPRODUTO
        FOREIGN KEY (IDPRODUTO)
                REFERENCES PRODUTO(IDPRODUTO)                    

);


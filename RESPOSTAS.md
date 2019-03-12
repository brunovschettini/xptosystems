# Folha de respostas
________________________________________
|Nome:| Bruno Vieira Schettini da Silva | 
________________________________________
|Data:| 11/03/2019                      |
________________________________________

## Parte 1 – SQL Conceitual 

* Respostas 

|  Q  | R     |
| ----------- |
|  1  | A     |
|  2  | B , E |
|  3  | A     | 
|  4  | D     |
|  5  | A     |

## Parte 2 – SQL Prático

* Exercício 1.

    -- DROP TABLE PRODUTO;

    create table PRODUTO (
        IDPRODUTO number not null constraint produto_pk primary key,
        CODIGOINTERNO varchar2(60) not null,
        DESCR varchar2(120) not null,
        ATIVO char(1) char(1) DEFAULT 'S' not null
    );
    /


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

    /

Respostas: 

a)

    SELECT * FROM PRODUTO WHERE ROWNUM <= 10
        
b) 

    SELECT PRO.* 
    FROM PRODUTO PRO
    INNER JOIN EMBALAGEM EMB ON EMB.IDPRODUTO = PRO.IDPRODUTO
    AND EMB.ativo = 'S'

c)     

    SELECT EMB.IDPRODUTO, SUM(FATORCONVERSAO) as QTDE
    FROM PRODUTO PRO
    INNER JOIN EMBALAGEM EMB ON EMB.IDPRODUTO = PRO.IDPRODUTO
    GROUP BY EMB.IDPRODUTO

d) 

    -- INSERT INTO PRODUTO (IDPRODUTO, CODIGOINTERNO, DESCR, ATIVO) VALUES (435, '000005', 'JOHNNIE WALKER BLUE LABEL', 'S');
    INSERT INTO PRODUTO (IDPRODUTO, CODIGOINTERNO, DESCR, ATIVO) VALUES (436, '000006', 'FANTA GUARANÁ 600 ML', 'S');
    -- INSERT INTO EMBALAGEM (IDPRODUTO, BARRA, DESCR, FATORCONVERSAO, ALTURA, LARGURA, COMPRIMENTO, ATIVO) VALUES (435, '7891000745678', 'JOHNNIE WALKER BLUE LABEL', 15, 300, 150, 150, 'S');
    INSERT INTO EMBALAGEM (IDPRODUTO, BARRA, DESCR, FATORCONVERSAO, ALTURA, LARGURA, COMPRIMENTO, ATIVO) VALUES (436, '7894900093261', 'FANTA GUARANÁ 600 ML EMB COM 6', 6, 100, 100, 100, 'S');

e)     

    UPDATE EMBALAGEM 
    SET 
    ALTURA = 250,
    LARGURA = 120,
    COMPRIMENTO = 150
    WHERE FATORCONVERSAO = 1

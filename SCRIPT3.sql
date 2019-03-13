-- Folha de respostas
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019
-- Parte 3 – PL/SQL Prático
-- Exercício 3.
-- Crie uma procedure chamada DEFINIR_VALORES para que altere o valor dos itens 
-- da tabela EXAME_ITEMNF, definindo números inteiros aleatórios entre 1 e 100. 
-- Faça com que a linha da tabela EXAME_NF receba o valor da somatória de seus 
-- itens na coluna TOTALGERAL. Salve o fonte do script no arquivo SCRIPT3.SQL.

-- DROP PROCEDURE DEFINIR_VALROES();

CREATE OR REPLACE PROCEDURE DEFINIR_VALORES()


IS
    TOTAL number;
    new_value NUMBER;

BEGIN

    SELECT CAST(round(dbms_random.value(1,100)) AS number) INTO new_value FROM dual;


END;
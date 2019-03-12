-- Folha de respostas
-- Parte 3 – PL/SQL Prático
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019
-- Local do teste: https://livesql.oracle.com

-- Exercício 2.
-- Crie um segundo script (SCRIPT2.SQL) que possua um bloco de código para que gere registros fictícios para as tabelas criadas. Gere 1000 registros para a tabela EXAME_NF. Para cada EXAME_NF gere 3 registros. Faça com que a DATACADASTRO do EXAME_NF comece em 10 dias atrás, fazendo com que a cada 100 registros a data seja aumentada em 1 dia, distribuindo assim os 1000 registros em 10 dias diferentes de cadastro.


DECLARE 

amount NUMBER := 0;
valor NUMBER := 0;
DATACAD DATE := SYSDATE - 10;
x NUMBER := 0;
idnf NUMBER := 0;
itensx NUMBER := 0;

BEGIN
   FOR xcount IN 1..1000 LOOP
    
        IF x = 100  THEN 
            DATACAD := DATACAD + 1;
            x := 1;
            
        ELSE
            
            x := x + 1;
            
        END IF;
        
   -- SELECT 0 as vdsd INTO amount FROM dual;

        -- EXAME_NF 
        INSERT INTO EXAME_NF (IDNF, NUMERO, DATACADASTRO, TOTALGERAL) VALUES (xcount, xcount, DATACAD, 0);

        -- SELECT MAX(IDNF) INTO :idnf FROM EXAME_NF;
        
        -- SELECT round(dbms_random.value(1,100)) AS  FROM dual;
                         
        -- EXAME_ITEMNF 1                         
        SELECT CAST(round(dbms_random.value(1,100)) AS number) INTO amount FROM dual;
        SELECT round(dbms_random.value(1,1000), 2) INTO valor FROM dual;
        INSERT INTO EXAME_ITEMNF (IDNF, IDPRODUTO, NUMERO, QTDE, VALOR) VALUES (xcount, 1, (xcount * (1 + 1000 + amount)) , amount, valor);
        -- EXAME_ITEMNF 2
        SELECT CAST(round(dbms_random.value(1,100)) AS number) INTO amount FROM dual;
        SELECT round(dbms_random.value(1,1000), 2) INTO valor FROM dual;
        INSERT INTO EXAME_ITEMNF (IDNF, IDPRODUTO, NUMERO, QTDE, VALOR) VALUES (xcount, 1, (xcount * (1 + 1000 + amount)) , amount, valor);
        -- EXAME_ITEMNF 3
        SELECT CAST(round(dbms_random.value(1,100)) AS number) INTO amount FROM dual;
        SELECT round(dbms_random.value(1,1000), 2) INTO valor FROM dual;
        INSERT INTO EXAME_ITEMNF (IDNF, IDPRODUTO, NUMERO, QTDE, VALOR) VALUES (xcount, 1, (xcount * (1 + 1000 + amount)) , amount, valor);
        
        -- rodar teste
        -- Adicionando itens
        -- FOR itensx IN 1..3 LOOP

        --          SELECT round(dbms_random.value(1,100)) AS  FROM dual;

        --         INSERT INTO EXAME_ITEMNF (xcount, IDPRODUTO, NUMERO, QTDE, VALOR)
        --         VALUES (xcount, IDPRODUTO, (xcount + itensx + 1000) , amount, VALOR);
        
        --    END LOOP;
            
    END LOOP;
END;
 
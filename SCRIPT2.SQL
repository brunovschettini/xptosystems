-- Folha de respostas
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019

-- drop table EXAME_ITEMNF; 
-- drop table EXAME_NF;

-- Parte 3 – PL/SQL Prático
-- Exercício 2.

DECLARE 

DATACAD DATE := SYSDATE - 10;
x NUMBER := 0;

BEGIN
   FOR xcount IN 1..1000 LOOP
    
        IF x = 100  THEN 
            DATACAD := DATACAD + 1;
            x := 1;
            
        ELSE
            
            x := x + 1;
            
        END IF;
        
   
        INSERT INTO EXAME_NF (IDNF, NUMERO, DATACADASTRO, TOTALGERAL) 
        VALUES (xcount, xcount, DATACAD, 0);
    END LOOP;
END;